package fincode.service.impl;

import fincode.mapper.*;
import fincode.model.*;
import fincode.service.InvestRecommendService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author zlj
 * @date 2023/3/23
 */

@Service
public class InvestRecommendServiceImpl implements InvestRecommendService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private StockPriceMapper stockPriceMapper;

    @Resource
    private StockTipOverallMapper stockTipOverallMapper;

    @Resource
    private StockTipDailyMapper stockTipDailyMapper;

    @Resource
    private StockDetailMapper stockDetailMapper;

    @Resource
    private IndustryMapper industryMapper;

    @Override
    public List<InvestRecommendRes> list(int page, int limit) {
        List<InvestRecommendCompose> compose = new ArrayList<>();
        List<InvestRecommendRes> res = new ArrayList<>();

        StockPricePO stockPrice = stockPriceMapper.findOneByCompanyId("000001.SZ");
        if (stockPrice == null) {
            return null;
        }
        int lastUpdateDate = stockPrice.getTime();

        // 这样可以令平均值(也就是综合收益指数)覆盖 return_index 字段
        List<Map<String, Object>> stockTipOverall = stockTipOverallMapper.findReturnIndexGroupId();
        if (stockTipOverall == null) {
            return null;
        }

        for (Map<String, Object> item : stockTipOverall) {
            int stock_id = (int) item.get("stock_id");
            double return_index = (double) item.get("avg(return_index)");
            InvestRecommendCompose investRecommendCompose = new InvestRecommendCompose();
            investRecommendCompose.stockTipOverall.setStock_id(stock_id);
            investRecommendCompose.stockTipOverall.setReturn_index(return_index);
            compose.add(investRecommendCompose);
        }

        for (InvestRecommendCompose investRecommendCompose : compose) {
            int stock_id = investRecommendCompose.stockTipOverall.getStock_id();
            List<StockTipDailyPO> stockTipDailyPO = stockTipDailyMapper.findAll2(stock_id, lastUpdateDate);
            if (stockTipDailyPO != null) {
                investRecommendCompose.stockTipDaily.addAll(stockTipDailyPO);
            }
            else {
                return null;
            }
        }

        // 先按照 return_index 排序, 再将 type=buy 的排到前面
        compose.sort((o1, o2) -> {
            if (o1.stockTipOverall.getReturn_index() < o2.stockTipOverall.getReturn_index()) {
                return 1;
            }
            else if (o1.stockTipOverall.getReturn_index() > o2.stockTipOverall.getReturn_index()) {
                return -1;
            }
            return 0;
        });

        compose.sort((o1, o2) -> {
            String t1 = "", t2 = "";
            // TODO 简单采取策略：有买建议就标记为建议买入
            for (StockTipDailyPO s1 : o1.getStockTipDaily()) {
                if ("buy".equals(s1.getType())) {
                    t1 = "buy";
                    break;
                }
            }

            for (StockTipDailyPO s2 : o2.getStockTipDaily()) {
                if ("buy".equals(s2.getType())) {
                    t2 = "buy";
                    break;
                }
            }

            if ("buy".equals(t1) && !"buy".equals(t2)) {
                return -1;
            }
            else if (!"buy".equals(t1) && "buy".equals(t2)) {
                return 1;
            }
            else {
                if (o1.stockTipOverall.getReturn_index() < o2.stockTipOverall.getReturn_index()) {
                    return 1;
                }
                else if (o1.stockTipOverall.getReturn_index() > o2.stockTipOverall.getReturn_index()) {
                    return -1;
                }
                return 0;
            }
        });

        // Page 从 1 开始
        compose = compose.subList((page - 1) * limit, page * limit);

        for (InvestRecommendCompose investRecommendCompose : compose) {
            int stock_id = investRecommendCompose.stockTipOverall.getStock_id();
            StockDetailPO stockDetailPO = stockDetailMapper.find(stock_id);
            investRecommendCompose.stockDetail = stockDetailPO;
            if (stockDetailPO != null) {
                int industry_id = stockDetailPO.getIndustry_id();
                IndustryPO industryPO = industryMapper.find(industry_id);
                String tsCode = stockDetailPO.getTs_code();
                StockPricePO stockPricePO = stockPriceMapper.find(tsCode, lastUpdateDate);
                investRecommendCompose.industry = industryPO;
                investRecommendCompose.stockPrice = stockPricePO;
            }
        }

        for (InvestRecommendCompose composeItem : compose) {
            InvestRecommendRes resItem = new InvestRecommendRes();
            resItem.setIndustry(composeItem.getIndustry());
            resItem.setStockDetail(composeItem.getStockDetail());
            resItem.setStockPrice(composeItem.getStockPrice());
            resItem.setStockTipOverall(composeItem.getStockTipOverall());

            for (StockTipDailyPO stockTipDailyPO : composeItem.getStockTipDaily()) {
                if ("buy".equals(stockTipDailyPO.getType())) {
                    resItem.setStockTipDaily(stockTipDailyPO);
                    break;
                }
            }

            res.add(resItem);
        }

        return res;


    }

}

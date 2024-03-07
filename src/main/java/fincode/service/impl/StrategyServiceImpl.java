package fincode.service.impl;

import fincode.mapper.StockPriceMapper;
import fincode.mapper.StockTipDailyMapper;
import fincode.mapper.StockTipOverallMapper;
import fincode.mapper.StrategyMapper;
import fincode.model.*;
import fincode.service.StrategyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class StrategyServiceImpl implements StrategyService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private StrategyMapper strategyMapper;

    @Resource
    private StockPriceMapper stockPriceMapper;

    @Resource
    private StockTipDailyMapper stockTipDailyMapper;

    @Resource
    private StockTipOverallMapper stockTipOverallMapper;

    @Override
    public ResultVO<StrategyVO> getDetail(int id) {
        StrategyPO queryOne = strategyMapper.findOne(id);

        if (queryOne == null) {
            logger.error("没有相关策略");
            return new ResultVO<>(1,"没有相关策略");
        }

        int matchCnt = stockTipOverallMapper.countMatch(id), profitCnt = stockTipOverallMapper.countProfit(id);
        StrategyVO strategyVO = new StrategyVO(queryOne);

        strategyVO.setMatchCnt(matchCnt);
        strategyVO.setProfitCnt(profitCnt);

        return new ResultVO<>(0,"success", strategyVO);
    }

    @Override
    public ResultVO<List<StrategyPO>> list() {
        List<StrategyPO> getAll = strategyMapper.findAll();

        if (getAll == null) {
            logger.error("找不到策略");
            return new ResultVO<>(1,"没有相关策略");
        }
        return new ResultVO<>(0,"success",getAll);
    }

    @Override
    public ResultVO<List<StockTipOverallPO>> listMatchStock(StrategyFrontListVO strategyFrontListVO) {
        String orderBy = strategyFrontListVO.getOrderBy();

        int page = strategyFrontListVO.getPage();
        int limit = strategyFrontListVO.getLimit();

        int start = (page - 1) * limit;
        int pageSize = limit;

        if (orderBy.equals("latest")){
            StockPricePO stockPricePO = stockPriceMapper.findOneByTimeDesc();
            int latestTradeDate = stockPricePO.getTime();

            List<StockTipDailyPO> lists = stockTipDailyMapper.findAllLatest(strategyFrontListVO.getId(),
                                                                            latestTradeDate, start, pageSize);

            Set<Integer> stockIds = new HashSet<>();
            for (StockTipDailyPO stockTipDailyPO : lists){
                stockIds.add(stockTipDailyPO.getStock_id());
            }

            List<StockTipOverallPO> res = new ArrayList<>();
            for (int stock_id : stockIds){
                StockTipOverallPO find = stockTipOverallMapper.findAllByStockId(strategyFrontListVO.getId(), stock_id);
                if (find != null) res.add(find);
            }

            return new ResultVO<>(0,"success",res);
        }
        else{
            String orderByField = "match_rate";
            if(orderBy.equals("profit")) orderByField = "profit_rate";
            List<StockTipOverallPO> res = stockTipOverallMapper.findAllByOrder(strategyFrontListVO.getId(), orderByField, start, pageSize);

            return new ResultVO<>(0,"success",res);
        }
    }
}

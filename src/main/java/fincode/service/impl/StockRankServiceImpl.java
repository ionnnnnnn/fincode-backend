package fincode.service.impl;

import fincode.mapper.StockRankMapper;
import fincode.model.IndustryPO;
import fincode.model.StockDetailPO;
import fincode.model.StockPricePO;
import fincode.model.StockRankInfo;
import fincode.service.StockRankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StockRankServiceImpl implements StockRankService {
    @Autowired
    StockRankMapper stockRankMapper;
    String[] sortCondition = {"pct_chg desc","pct_chg","vol desc","amount desc"};
    @Override
    public List<StockRankInfo> getStockRank(Integer sortType, Integer page, Integer limit) {
        int startIndex = (page-1) * limit;
        int endIndex = startIndex + limit;
        List<StockRankInfo> res = new ArrayList<>();
        // TODO: StockRankMapper.getLastUpdateDate
        Integer lastUpdateDate = stockRankMapper.getLastUpdateDateInStockPrice();
        // TODO: get today's rank in StockPrice
        List<StockPricePO> rank = stockRankMapper.getTodayRank(lastUpdateDate, sortCondition[sortType]);
        // TODO: get stockdetail where stockprice.companyId = stockdetail.ts_code
        int count = 0;
        for(StockPricePO stockPricePO:rank){
            StockDetailPO stockDetailPO = stockRankMapper.selectDetailByTSCode(stockPricePO.getCompanyId());
            if (stockDetailPO != null) {
                count++;
                res.add(new StockRankInfo(stockPricePO, stockDetailPO));
                if (count >= endIndex) break;
            }
        }
        // TODO: get industry info where industry.id = stockdetail.industry_id
        for(StockRankInfo info:res){
            IndustryPO industryPO = stockRankMapper.selectIndustryById(info.getStockDetailPO().getIndustry_id());
            info.setIndustryPO(industryPO);
        }
//        int startIndex = (page-1) * limit;
//        int endIndex = Math.min(startIndex+limit, res.size());
//        if(startIndex>=res.size()) return null;
        return res.subList(startIndex, endIndex);
    }
}

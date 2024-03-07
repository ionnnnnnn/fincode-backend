package fincode.service;

import fincode.model.StockTipInfo;
import fincode.model.StockTipLatestInfo;
import fincode.model.StockTipOverallInfo;
import fincode.model.StockWithStrategyTipInfo;

import java.util.List;

/**
 * @author zlj
 * @date 2023/4/7
 */
public interface StockTipService {

    StockTipInfo listTipsByCode(String stockCode, List<Integer> strategyIdList, int startDate, int endDate);

    StockTipInfo listTipsById(int stockId, List<Integer> strategyIdList, int startDate, int endDate);

    List<StockTipOverallInfo> listOverallById(int stockId);

    // 列出某些股票某些策略的最新
    List<StockTipLatestInfo> listLatest(List<Integer> stockIdList, List<Integer> strategyIdList);

    StockWithStrategyTipInfo getPeriodRate(int stockId, int strategyId, int startDate, int endDate);

}

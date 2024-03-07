package fincode.service.impl;

import fincode.mapper.*;
import fincode.model.*;
import fincode.service.StockTipService;
import fincode.utils.RateUtils;
import fincode.utils.TimeUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author zlj
 * @date 2023/4/7
 */
@Service
public class StockTipServiceImpl implements StockTipService {

    @Resource
    private StockMapper stockMapper;

    @Resource
    private StockTipDailyMapper stockTipDailyMapper;

    @Resource
    private StockTipOverallMapper stockTipOverallMapper;

    @Resource
    private StrategyMapper strategyMapper;

    @Resource
    private StockPriceMapper stockPriceMapper;

    @Override
    public StockTipInfo listTipsByCode(String stockCode, List<Integer> strategyIdList, int startDate, int endDate) {
        List<StockPO> res = stockMapper.findByCode(stockCode);
        if (res == null || res.isEmpty()) return null;

        return listTipsById(res.get(0).getId(), strategyIdList, startDate, endDate);
    }

    @Override
    public StockTipInfo listTipsById(int stockId, List<Integer> strategyIdList, int startDate, int endDate) {
        List<StockTipDailyPO> tipDailyList = new ArrayList<>();
        for (int strategy_id : strategyIdList) {
            List<StockTipDailyPO> find = stockTipDailyMapper.findAll(stockId, strategy_id, startDate, endDate);
            if (find != null && !find.isEmpty()) tipDailyList.addAll(find);
        }

        if (tipDailyList.isEmpty()) return null;

        // 升序排列日期
        tipDailyList.sort(Comparator.comparingInt(StockTipDailyPO::getTrade_date));

        // 按照先后逻辑提取有效组合策略点
        StockTipInfo res = new StockTipInfo();
        List<StockTip> tipList = assembleTipListSequence(tipDailyList);
        res.setTipList(tipList);

        // 冗余划分两种tip类型
        List<StockTip> buyTipList = new ArrayList<>();
        List<StockTip> sellTipList = new ArrayList<>();
        tipList.forEach(stockTip -> {
            if ("buy".equals(stockTip.getType())) {
                buyTipList.add(stockTip);
            }
            else if ("sell".equals(stockTip.getType())) {
                sellTipList.add(stockTip);
            }
        });

        res.setBuyTipList(buyTipList);
        res.setSellTipList(sellTipList);

        // 计算匹配率和收益率
        double matchRate = RateUtils.getMatchRateFromDO(tipDailyList, endDate, startDate);
        double profitRate = RateUtils.getProfitRateFromDO(tipDailyList);
        res.setMatchRate(matchRate);
        res.setProfitRate(profitRate);

        return res;
    }

    @Override
    public List<StockTipOverallInfo> listOverallById(int stockId) {
        List<StockTipOverallPO> overallList = stockTipOverallMapper.findAllStrategyForOneStock(stockId);
        if (overallList == null) return null;
        List<StrategyPO> strategyList = strategyMapper.findAll();

        HashMap<Integer, String> strategyMap = new HashMap<>();
        strategyList.forEach(strategyPO -> {
            strategyMap.put(strategyPO.getId(), strategyPO.getName());
        });

        List<Integer> strategyIdList = new ArrayList<>();
        strategyList.forEach(strategyPO -> {
            strategyIdList.add(strategyPO.getId());
        });

        HashMap<Integer, String> tipMap = getLatest(stockId, strategyIdList);
        if (tipMap == null) return null;

        List<StockTipOverallInfo> res = new ArrayList<>();

        for (StockTipOverallPO stockTipOverallPO : overallList) {
            String tip = tipMap.get(stockTipOverallPO.getStrategy_id());
            if (tip == null) tip = "";
            StockTipOverallInfo info = new StockTipOverallInfo();
            info.setStockId(stockTipOverallPO.getStock_id());
            info.setStrategyId(stockTipOverallPO.getStrategy_id());
            info.setStrategyName(strategyMap.get(stockTipOverallPO.getStrategy_id()));
//            info.setHistoryProfitRate(stockTipOverallPO.getHistory_profit_rate());
//            info.setHistoryMatchRate(stockTipOverallPO.getHistory_match_rate());
//            info.setIndustryMatchRate(stockTipOverallPO.getIndustry_match_rate());
//            info.setProfitIndex(stockTipOverallPO.getProfit_index());
            info.setLatestTip(tip);
            res.add(info);
        }

        return res;
    }

    @Override
    public List<StockTipLatestInfo> listLatest(List<Integer> stockIdList, List<Integer> strategyIdList) {
        HashMap<Integer, List<StockTipDailyPO>> stockStrategyTipMap = getStrategyTipMap(stockIdList, strategyIdList);
        // 按StockId分组组装返回结果Info
        return assembleLatestTipInfo(stockIdList, strategyIdList, stockStrategyTipMap);
    }

    private List<StockTipLatestInfo> assembleLatestTipInfo(List<Integer> stockIdList, List<Integer> strategyIdList,
                                                           HashMap<Integer, List<StockTipDailyPO>> stockStrategyTipMap) {
        List<StockTipLatestInfo> res = new ArrayList<>();

        for (int id : stockIdList) {
            StockTipLatestInfo tipInfo = new StockTipLatestInfo();
            tipInfo.setStockId(id);
            tipInfo.setTipList(new ArrayList<>());
            tipInfo.setBuyTipStrategyIdList(new ArrayList<>());
            tipInfo.setSellTipStrategyIdList(new ArrayList<>());
            tipInfo.setOtherStrategyIdList(new ArrayList<>());

            List<StockTipDailyPO> tipList = stockStrategyTipMap.get(id);
            if (tipList != null && !tipList.isEmpty()) {
                tipInfo.setTipList(assembleTipList(tipList));

                HashMap<Integer, StockTipDailyPO> tipStrategyMap = new HashMap<>();
                tipList.forEach(stockTipDailyPO -> {
                    tipStrategyMap.put(stockTipDailyPO.getStrategy_id(), stockTipDailyPO);
                });

                for (int strategyId : strategyIdList) {
                    StockTipDailyPO tip = tipStrategyMap.get(strategyId);
                    if (tip != null) {
                        if ("buy".equals(tip.getType())) {
                            List<Integer> get = tipInfo.getBuyTipStrategyIdList();
                            get.add(strategyId);
                            tipInfo.setBuyTipStrategyIdList(get);
                        }
                        if ("sell".equals(tip.getType())) {
                            List<Integer> get = tipInfo.getSellTipStrategyIdList();
                            get.add(strategyId);
                            tipInfo.setSellTipStrategyIdList(get);
                        }
                    }
                    else {
                        List<Integer> get = tipInfo.getOtherStrategyIdList();
                        get.add(strategyId);
                        tipInfo.setOtherStrategyIdList(get);
                    }
                }
            } else {
                tipInfo.setOtherStrategyIdList(strategyIdList);
            }

            res.add(tipInfo);
        }

        return res;
    }

    // 简单转换策略点
    private List<StockTip> assembleTipList(List<StockTipDailyPO> tipDailyList) {
        List<StockTip> tipList = new ArrayList<>();
        for (StockTipDailyPO tipDaily : tipDailyList) {
            StockTip tip = new StockTip();
            tip.setStrategyId(tipDaily.getStrategy_id());
            tip.setTradeDate(tipDaily.getTrade_date());
            tip.setType(tipDaily.getType());
            tip.setOpen(tipDaily.getOpen());
            tip.setClose(tipDaily.getClose());
            tipList.add(tip);
        }

        return tipList;
    }


    private HashMap<Integer, List<StockTipDailyPO>> getStrategyTipMap(List<Integer> stockIdList, List<Integer> strategyIdList) {
        List<StockPO> stockList = new ArrayList<>();
        for (int stockId : stockIdList) {
            List<StockPO> find = stockMapper.findByIdLimit100(stockId);
            if (find != null && !find.isEmpty()) stockList.addAll(find);
        }

        List<String> stockCodeList = new ArrayList<>();
        stockList.forEach(stockPO -> {
            stockCodeList.add(stockPO.getTs_code());
        });

        int maxTime = Integer.MIN_VALUE;
        StockPricePO stockPrice = null;
        for (String companyId : stockCodeList) {
            StockPricePO find = stockPriceMapper.findOneByCompanyId(companyId);
            if (find != null && find.getTime() >= maxTime) {
                maxTime = find.getTime();
                stockPrice = find;
            }
        }

        int latestTradeDate = stockPrice.getTime();

        List<StockTipDailyPO> tipList = new ArrayList<>();
        for (int stockId : stockIdList) {
            for (int strategyId : strategyIdList) {
                List<StockTipDailyPO> find = stockTipDailyMapper.findAllByTradeDate(stockId,strategyId,latestTradeDate);
                if (find != null) tipList.addAll(find);
            }
        }

        HashMap<Integer, List<StockTipDailyPO>> stockStrategyTipMap = new HashMap<>();
        tipList.forEach(stockTipDailyPO -> {
            List<StockTipDailyPO> get = stockStrategyTipMap.getOrDefault(stockTipDailyPO.getStock_id(), new ArrayList<>());
            get.add(stockTipDailyPO);
            stockStrategyTipMap.put(stockTipDailyPO.getStock_id(), get);
        });

        return stockStrategyTipMap;
    }


    private HashMap<Integer, String> getLatest(int stockId, List<Integer> strategyIdList) {
        List<StockPO> stockPOS = stockMapper.findById(stockId);
        StockPO stock = null;
        if (stockPOS!= null && !stockPOS.isEmpty()) {
            stock = stockPOS.get(0);
        }
        if (stock == null) return null;

        String code = stock.getTs_code();
        StockPricePO stockPrice = stockPriceMapper.findOneByCompanyId(code);
        if (stockPrice == null) return null;
        int latestTradeDate = stockPrice.getTime();

        List<StockTipDailyPO> tipList = new ArrayList<>();
        for (int strategy_id : strategyIdList) {
            List<StockTipDailyPO> find = stockTipDailyMapper.findAllByTradeDate(stockId, strategy_id, latestTradeDate);
            if (find != null && !find.isEmpty()) tipList.addAll(find);
        }
//        if (tipList.isEmpty()) return null;

        HashMap<Integer, String> res = new HashMap<>();
        for (int strategy_id : strategyIdList) {
            res.put(strategy_id, "");
        }

        tipList.forEach(stockTipDailyPO -> {
            res.put(stockTipDailyPO.getStrategy_id(), stockTipDailyPO.getType());
        });

        return res;
    }


    // 按照先后逻辑提取有效组合策略点
    // 遵循：第一个买卖点加入列表，其后每遇到一个与上一买卖点类型不同的点则加入列表
    private List<StockTip> assembleTipListSequence(List<StockTipDailyPO> tipDailyList) {
        String lastType = "";
        int lastDate = 0;

        // index用于判断当前是否是一个买卖闭环内
        int index = 0;
        List<StockTip> tipList = new ArrayList<>();
        for (StockTipDailyPO tipDaily : tipDailyList) {
            // 买卖点类型交替生效，并且同一天只有一个买/卖点有效
            boolean isInLoop = index % 2 == 1;
            boolean isDifferentType = !tipDaily.getType().equals(lastType);
            boolean isDifferentDay = tipDaily.getTrade_date() != lastDate;

            if ((!isInLoop || isDifferentType) && isDifferentDay) {
                lastType = tipDaily.getType();
                lastDate = tipDaily.getTrade_date();
                index++;
                StockTip tip = new StockTip();
                tip.setStrategyId(tipDaily.getStrategy_id());
                tip.setTradeDate(tipDaily.getTrade_date());
                tip.setType(tipDaily.getType());
                tip.setOpen(tipDaily.getOpen());
                tip.setClose(tipDaily.getClose());
                tipList.add(tip);
            }
        }

        return tipList;
    }

    @Override
    public StockWithStrategyTipInfo getPeriodRate(int stockId, int strategyId, int startDate, int endDate) {
        List<StockTipDailyPO> tipList = stockTipDailyMapper.findAll(stockId, strategyId, startDate, endDate);

        // 升序排列日期
        tipList.sort((o1, o2) -> o2.getTrade_date() - o1.getTrade_date());

        StockWithStrategyTipInfo res = new StockWithStrategyTipInfo();
        res.setStockId(stockId);
        res.setStrategyId(strategyId);

        // 计算选定日期区间收益率
        double profitRate = RateUtils.getPeriodProfitRateFromDO(tipList);
        double period = TimeUtils.getPeriod(startDate, endDate);

        // 计算匹配率
        double matchRate = RateUtils.getPeriodMatchRateFromDO(tipList);
        res.setProfitRate(profitRate / period);
        res.setMatchRate(matchRate);

        return res;
    }

}


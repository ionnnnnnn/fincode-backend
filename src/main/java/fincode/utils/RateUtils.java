package fincode.utils;

import fincode.model.StockTipDailyPO;

import java.time.LocalDate;
import java.util.List;

/**
 * @author zlj
 * @date 2023/4/7
 */
public class RateUtils {

    public static double getMatchRateFromDO(List<StockTipDailyPO> tipDailyList, int endDate, int startDate) {
        int matchDays = tipDailyList.size();
        // todo:更换为真实交易日
        long totalDays = Math.abs(TimeUtils.convertTimeFromInt(endDate).toEpochDay() - TimeUtils.convertTimeFromInt(startDate).toEpochDay()) + 1;
        return (double) matchDays / totalDays;
    }

    public static double getProfitRateFromDO(List<StockTipDailyPO> tipDailyList) {
        double total = 10000.0;
        double curMoney = total;
        double lastCost = 1.0;

        for (int index = 0 ; index < tipDailyList.size() ; index++) {
            StockTipDailyPO a = tipDailyList.get(index);
            // 说明一次买卖闭环
            if (index % 2 != 0) {
                // 买卖闭环
                if ("sell".equals(a.getType())) {
                    // 目前用收盘价模拟
                    curMoney *= a.getClose() / lastCost;
                }

                // 卖买闭环
                if ("buy".equals(a.getType())) {
                    curMoney /= a.getClose() / lastCost;
                }
            }
            lastCost = a.getClose();
        }

        return curMoney / total - 1.0;
    }

    public static double getPeriodProfitRateFromDO(List<StockTipDailyPO> tipDailyList) {
        double total = 10000.0;
        double curMoney = total;
        double lastCost = 1.0;

        for (StockTipDailyPO stockTipDailyPO : tipDailyList) {
            if ("sell".equals(stockTipDailyPO.getType()) || "loss_sell".equals(stockTipDailyPO.getType()) || "profit_sell".equals(stockTipDailyPO.getType())) {
                // 目前用收盘价模拟
                curMoney *= stockTipDailyPO.getClose() / lastCost;
            }

            if ("buy".equals(stockTipDailyPO.getType())) {
                curMoney /= stockTipDailyPO.getClose() / lastCost;
            }

            lastCost = stockTipDailyPO.getClose();
        }

        return curMoney / total - 1.0;

    }

    public static double getPeriodMatchRateFromDO(List<StockTipDailyPO> tipDailyList) {
        double total = 0;
        double hit = 0;

        for (StockTipDailyPO a : tipDailyList) {
            if ("sell".equals(a.getType()) || "buy".equals(a.getType())) {
                total += 1;
                if (a.getIs_hit() == 1) {
                    hit += 1;
                }
            }
        }

        if (total == 0) return 0;
        return hit / total;
    }

}

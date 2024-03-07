package fincode.model;

import lombok.Data;

/**
 * @author zlj
 * @date 2023/4/13
 */
@Data
public class StockTipOverallInfo {

    private int stockId;

    private int strategyId;

    private String strategyName;

    private double historyProfitRate;

    private double historyMatchRate;

    private double industryMatchRate;

    private double profitIndex;

    private String latestTip;

}

package fincode.model;

import lombok.Data;

/**
 * @author zlj
 * @date 2023/4/25
 */

@Data
public class StockWithStrategyTipInfo {
    int stockId;

    int strategyId;

    //TipList     []*StockTip
    //BuyTipList  []*StockTip
    //SellTipList []*StockTip

    double profitRate;

    double matchRate;
}

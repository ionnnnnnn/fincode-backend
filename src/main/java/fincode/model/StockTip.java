package fincode.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author zlj
 * @date 2023/4/7
 */
@Getter
@Setter
public class StockTip {
    int strategyId;

    int tradeDate;

    String type;

    double open;

    double close;
}

package fincode.model.req.stocktip;

import lombok.Data;

/**
 * @author zlj
 * @date 2023/4/25
 */

@Data
public class StockTipApiPeriodRateReq {

    int stockId;

    int strategyId;

    String startDate;

    String endDate;
}

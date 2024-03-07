package fincode.model.req.stockPrice;

import lombok.Data;

/**
 * @author zlj
 * @date 2023/4/25
 */

@Data
public class StockPriceApiListPriceByIdReq {

    int stockId;

    String endDate;

    int limit;
}

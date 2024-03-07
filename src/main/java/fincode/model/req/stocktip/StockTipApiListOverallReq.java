package fincode.model.req.stocktip;

import lombok.Data;

import javax.validation.constraints.Min;

/**
 * @author zlj
 * @date 2023/4/13
 */

@Data
public class StockTipApiListOverallReq {
    @Min(value = 1, message = "股票Id不能为空")
    int stockId;
}

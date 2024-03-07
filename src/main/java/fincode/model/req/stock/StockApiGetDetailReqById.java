package fincode.model.req.stock;

import lombok.Data;

import javax.validation.constraints.Min;

/**
 * @author zlj
 * @date 2023/4/24
 */
@Data
public class StockApiGetDetailReqById {

    @Min(value = 1, message = "stock_id should be greater than or equal 1")
    private Integer id;
}

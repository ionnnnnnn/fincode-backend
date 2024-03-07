package fincode.model;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class StockFollowedVO {
    @Min(value = 1, message = "stock_id should be greater than or equal 1")
    private Integer stockId;

    public StockFollowedVO(Integer stockId) {
        this.stockId = stockId;
    }

    public StockFollowedVO() {
    }
}

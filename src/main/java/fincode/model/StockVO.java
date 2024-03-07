package fincode.model;

import lombok.Data;

import javax.validation.constraints.Min;

@Data
public class StockVO {
    @Min(value = 1, message = "stock_id should be greater than or equal 1")
    private Integer stockId;

    private String stockCode;
    public StockVO(){

    }


}

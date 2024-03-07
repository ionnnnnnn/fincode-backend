package fincode.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StockFollowedPO {
    private Integer id;
    private Integer userId;

    private Integer stockId;
    private LocalDateTime gmtCreated;

    private LocalDateTime gmtModified;



    public StockFollowedPO(Integer user_id, Integer stock_id) {
        this.userId = user_id;
        this.stockId = stock_id;
    }
}

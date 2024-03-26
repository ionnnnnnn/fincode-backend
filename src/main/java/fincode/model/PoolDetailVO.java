package fincode.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zlj
 * @date 2023/6/7
 */

@Data
public class PoolDetailVO {
    // todo 修改
    private Integer pool_id;

    private String pool_name;

    private String strategy_name;

    private String condition;

    private List<StockTipDailyPO>  stocks = new ArrayList<>();

    public PoolDetailVO(){}

    public PoolDetailVO(StockPoolPO stockPoolPO){
        this.pool_id = stockPoolPO.getId();
        this.pool_name = stockPoolPO.getPool_name();
        this.condition = stockPoolPO.getCondition();
    }

    public void addStockTip(StockTipDailyPO stockTipDailyPO) {
        stocks.add(stockTipDailyPO);
    }
}


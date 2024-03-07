package fincode.model.req.pool;

import lombok.Data;

/**
 * @author zlj
 * @date 2023/6/7
 */
@Data
public class PoolCreateReq {
    private String type;

    private String strategy_name;

    private Integer return_index;

    private String pool_name;

    private String start;

    private String end;

    private String buy_or_sell;

    private String industry_name;
}

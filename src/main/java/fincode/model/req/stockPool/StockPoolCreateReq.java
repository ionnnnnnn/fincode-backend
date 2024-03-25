package fincode.model.req.stockPool;

import lombok.Data;

/**
 * @author lzz
 * @date 2024/3/25
 */
@Data
public class StockPoolCreateReq {
    private Integer user_id;

    private String pool_name;

    private String condition;
}

package fincode.model.req.stockPool;

import lombok.Data;

/**
 * @author lzz
 * @date 2024/3/25
 */
@Data
public class StockPoolDeleteReq {
    private Integer user_id;

    private Integer pool_id;
}

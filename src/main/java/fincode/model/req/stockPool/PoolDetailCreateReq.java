package fincode.model.req.stockPool;

import lombok.Data;

/**
 * @author lzz
 * @date 2024/3/25
 */
@Data
public class PoolDetailCreateReq {
    Integer poolId;
    Integer stockId;
    Integer strategyId;
}

package fincode.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author zlj
 * @date 2023/6/7
 */

@Getter
@Setter
public class PoolDetailPO {
    private Integer id;

    private Integer pool_id;

    private Integer stock_id;

    // 加策略
    private Integer strategy_id;
}

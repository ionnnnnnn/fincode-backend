package fincode.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author lzz
 * @date 2024/3/27
 */
@Getter
@Setter
public class StockPoolDetailVO {
    // StockPoolDetail是股票池中的项，每项包含的信息有一支股票，一个策略（和一个建议？
    private Integer pool_id;

    private Integer stock_id;

    private Integer strategy_id;

    private StockVO stockVO;

    private StrategyVO strategyVO;
}

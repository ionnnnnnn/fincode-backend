package fincode.service;

import fincode.model.*;

import java.util.List;

public interface StrategyService {

    //获取单个策略
    ResultVO<StrategyVO> getDetail(int id);

    //获取所有策略
    ResultVO<List<StrategyPO>> list();

    ResultVO<List<StockTipOverallPO>> listMatchStock(StrategyFrontListVO strategyFrontListVO);
}

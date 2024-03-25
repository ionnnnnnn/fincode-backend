package fincode.service;

import fincode.model.*;
import fincode.model.req.stockPool.StockPoolCreateReq;
import fincode.model.req.stockPool.StockPoolDeleteReq;

import java.util.List;

/**
 * @author lzz
 * @date 2024/3/24
 */
public interface StockPoolService {
    // 用户新建股票池
    boolean createPool(StockPoolCreateReq stockPoolCreateReq);

    // 用户删除股票池
    boolean deletePool(StockPoolDeleteReq stockPoolDeleteReq);

    // 将股票加入股票池
    boolean addStockToPool();

    // 获取用户所有股票池
    List<StockPoolPO> getUserPoolList(Integer userId);

    // 获取股票池中所有股票
    List<PoolDetailPO> getPoolStockList(Integer poolId);

    // 获取股票池中一支股票
    StockVO getPoolStock();


}

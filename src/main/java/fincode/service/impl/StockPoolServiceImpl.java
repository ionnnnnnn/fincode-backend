package fincode.service.impl;

import fincode.mapper.PoolMapper;
import fincode.mapper.StockMapper;
import fincode.model.PoolDetailPO;
import fincode.model.StockPoolPO;
import fincode.model.StockVO;
import fincode.model.req.stockPool.StockPoolCreateReq;
import fincode.model.req.stockPool.StockPoolDeleteReq;
import fincode.service.StockPoolService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lzz
 * @date 2024/3/25
 */

@Service
public class StockPoolServiceImpl implements StockPoolService {

    @Resource
    private PoolMapper poolMapper;

    @Resource
    private StockMapper stockMapper;


    public boolean createPool(StockPoolCreateReq stockPoolCreateReq){
        StockPoolPO stockPoolPO = new StockPoolPO();
        stockPoolPO.setUser_id(stockPoolCreateReq.getUser_id());
        stockPoolPO.setPool_name(stockPoolCreateReq.getPool_name());
        stockPoolPO.setCondition(stockPoolCreateReq.getCondition());
        // 剩余gmt created modified
        poolMapper.createPool(stockPoolPO);
        return true;
    }

    public boolean deletePool(StockPoolDeleteReq stockPoolDeleteReq){
        // 判断该用户是否拥有该股票池
        if(poolMapper.findOne(stockPoolDeleteReq.getUser_id(), stockPoolDeleteReq.getPool_id()) == null)
            return false;
        // 删除
        poolMapper.deletePool(stockPoolDeleteReq.getPool_id(), stockPoolDeleteReq.getUser_id());
        return true;
    }

    public boolean addStockToPool(){
        return true;
    }

    public List<StockPoolPO> getUserPoolList(Integer userId){
        return poolMapper.findAll(userId);
    }

    public List<PoolDetailPO> getPoolStockList(Integer poolId){
        return poolMapper.findAllTips(poolId);
    }

    public StockVO getPoolStock(){
        return null;
    }
}

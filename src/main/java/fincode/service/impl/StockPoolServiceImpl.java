package fincode.service.impl;

import fincode.mapper.PoolMapper;
import fincode.mapper.StockMapper;
import fincode.model.*;
import fincode.model.req.stockPool.PoolDetailCreateReq;
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

    public boolean addStockToPool(PoolDetailCreateReq poolDetailCreateReq){
        // 判断股票池中是否已有该股票
        if(poolMapper.findOneStock(poolDetailCreateReq.getPoolId(), poolDetailCreateReq.getStockId()) != null)
            return false;
        // 加入
        PoolDetailPO po = new PoolDetailPO();
        po.setPool_id(poolDetailCreateReq.getPoolId());
        po.setStock_id(poolDetailCreateReq.getStockId());
        po.setStrategy_id(poolDetailCreateReq.getStrategyId());
        poolMapper.insertStockToPool(po);
        return true;
    }

    public List<StockPoolVO> getUserPoolList(Integer userId){
        // 获取列表
        List<StockPoolPO> pos = poolMapper.findAll(userId);
        // 转换为 VO
        List<StockPoolVO> vos = new ArrayList<>();
        for (StockPoolPO po : pos) {
            StockPoolVO tmp = new StockPoolVO();
            tmp.setId(po.getId());
            tmp.setUser_id(po.getUser_id());
            tmp.setPool_name(po.getPool_name());
            tmp.setCondition(po.getCondition());
            vos.add(tmp);
        }
        return vos;
    }

    public List<PoolDetailVO> getPoolStockList(Integer poolId){
        // 获取列表
        List<PoolDetailPO> pos = poolMapper.findAllTips(poolId);
        // 转换为 VO
        List<PoolDetailVO> vos = new ArrayList<>();
        // todo
        return vos;
    }

    public StockVO getPoolStock(){
        return null;
    }
}

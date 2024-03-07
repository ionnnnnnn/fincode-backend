package fincode.service.impl;

import fincode.mapper.*;
import fincode.model.*;
import fincode.model.req.pool.PoolCreateReq;
import fincode.model.req.pool.PoolDeleteReq;
import fincode.model.req.pool.PoolDetailReq;
import fincode.service.PoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zlj
 * @date 2023/6/7
 */

@Service
public class PoolServiceImpl implements PoolService {

    @Resource
    private StrategyMapper strategyMapper;

    @Resource
    private PoolMapper poolMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private StockTipDailyMapper stockTipDailyMapper;

    @Resource
    private StockDetailMapper stockDetailMapper;

    /*
    获取user_id
    若登陆，返回user_id
    若登陆信息无效，返回null
     */
    private Integer getUserId(HttpSession session, HttpServletRequest req, HttpServletResponse resp){
        Cookie[] cookies = req.getCookies();

        // 没有cookie
        if (null == cookies) {
            return null;
        }

        // 定义cookie_username，用户的一些登录信息，例如：用户名，密码等
        String cookie_username = null;
        // 获取cookie里面的一些用户信息
        for (Cookie item : cookies) {
            if ("cookie_username".equals(item.getName())) {
                cookie_username = item.getValue();
                break;
            }
        }
        //  无效cookie，不含用户名
//        if (cookie_username == null || "".equals(cookie_username)) {
//            return null;
//        }

        // 获取我们登录后存在session中的用户信息，如果为空，表示session已经过期
        Object obj = session.getAttribute("username");

        if (obj == null) {
            return null;
        }

//        if(!cookie_username.equals(obj)) return null;
        UserPO user = userMapper.findByPassport((String) obj);
        if(user==null) return null;
        return user.getId();
    }


    @Override
    public ResultVO<PoolDetailVO> createPool(PoolCreateReq poolCreateReq, HttpSession session, HttpServletRequest req, HttpServletResponse resp) {
        Integer user_id = getUserId(session,req,resp);
        if(user_id==null) return null;

        String type = poolCreateReq.getType();
        if ("strategy".equals(type)) {
            String strategy_name = poolCreateReq.getStrategy_name();
            StrategyPO strategy = strategyMapper.findByName(strategy_name);
            if (strategy == null) return null;
            StringBuilder condition = new StringBuilder();
            condition.append("strategy: ").append(strategy.getName()).append(" ;");
            String startDate = poolCreateReq.getStart();
            String endDate = poolCreateReq.getEnd();
            int start_date = Integer.parseInt(startDate.substring(0,4) + startDate.substring(5,7) + startDate.substring(8));
            int end_date = Integer.parseInt(endDate.substring(0,4) + endDate.substring(5,7) + endDate.substring(8));
            condition.append("start: ").append(startDate).append(" ;").append("end: ").append(endDate).append(" ;");

            List<StockTipDailyPO> poolContext = stockTipDailyMapper.findAllByDate(strategy.getId(), start_date, end_date);
            condition.append("type: ");
            if (poolCreateReq.getBuy_or_sell() != null && !"".equals(poolCreateReq.getBuy_or_sell())){
                poolContext = handleByType(poolContext, poolCreateReq.getBuy_or_sell());
                condition.append(poolCreateReq.getBuy_or_sell()).append(" ;");
            }
            condition.append("all ;");

            condition.append("industry: ");
            if (poolCreateReq.getIndustry_name() != null && !"".equals(poolCreateReq.getIndustry_name())){
                //TODO
                condition.append(poolCreateReq.getIndustry_name()).append(" ;");
            }
            condition.append("all ;");


            StockPoolPO stockPoolPO = new StockPoolPO();
            stockPoolPO.setCondition(condition.toString());
            stockPoolPO.setPool_name(poolCreateReq.getPool_name());
            stockPoolPO.setUser_id(user_id);
            int create = poolMapper.createPool(stockPoolPO);

            PoolDetailVO res = new PoolDetailVO(stockPoolPO);
            res.setStrategy_name(strategy_name);

            for (StockTipDailyPO stockTipDailyPO : poolContext) {
                PoolDetailPO poolDetailPO = new PoolDetailPO();
                poolDetailPO.setPool_id(stockPoolPO.getId());
                poolDetailPO.setStock_id(stockTipDailyPO.getId());
                int insert = poolMapper.insertPoolContext(poolDetailPO);
                res.addStockTip(stockTipDailyPO);
            }

            return new ResultVO<>(0, "创建成功", res);


        }
        else if ("return_index".equals(type)){
            return null;
        }
        else {
            return null;
        }
    }

    @Override
    public boolean deletePool(PoolDeleteReq poolDeleteReq, HttpSession session, HttpServletRequest req, HttpServletResponse resp) {
        Integer user_id = getUserId(session,req,resp);
        if(user_id==null) return false;

        int delete = poolMapper.deletePool(poolDeleteReq.getPool_id(), user_id);
        return delete > 0;
    }

    @Override
    public List<PoolList> showAll(HttpSession session, HttpServletRequest req, HttpServletResponse resp) {
        Integer user_id = getUserId(session,req,resp);
        if(user_id==null) return null;

        List<StockPoolPO> stockPoolPOList = poolMapper.findAll(user_id);
        List<PoolList> res = new ArrayList<>();

        stockPoolPOList.forEach(stockPoolPO -> {
            PoolList pool = new PoolList();
            pool.setId(stockPoolPO.getId());
            pool.setPool_name(stockPoolPO.getPool_name());
            pool.setCondition(stockPoolPO.getCondition());
            String[] allConditions = stockPoolPO.getCondition().split(" ;");
            pool.setStrategy_name(allConditions[0]);
            pool.setStart(allConditions[1]);
            pool.setEnd(allConditions[2]);
            res.add(pool);
        });

        return res;

    }

    @Override
    public PoolDetail showDetail(PoolDetailReq poolDetailReq, HttpSession session, HttpServletRequest req, HttpServletResponse resp) {
        Integer user_id = getUserId(session,req,resp);
        if(user_id==null) return null;

        StockPoolPO stockPoolPO = poolMapper.findOne(user_id, poolDetailReq.getPool_id());
        List<PoolDetailPO> list = poolMapper.findAllTips(poolDetailReq.getPool_id());

        PoolDetail res = new PoolDetail();
        res.setPool_id(stockPoolPO.getId());
        res.setPool_name(stockPoolPO.getPool_name());
        res.setCondition(stockPoolPO.getCondition());
        String[] allConditions = stockPoolPO.getCondition().split(" ;");
        res.setStrategy_name(allConditions[0]);

        list.forEach(poolDetailPO -> {
            StockTipDailyPO stockTipDailyPO = stockTipDailyMapper.findOne(poolDetailPO.getStock_id());
            int stockId = stockTipDailyPO.getStock_id();
            StockDetailPO stockDetailPO = stockDetailMapper.find(stockId);
            res.insertPoolTip(stockTipDailyPO, stockDetailPO);
        });

        return res;
    }

    private List<StockTipDailyPO> handleByType(List<StockTipDailyPO> poolContext, String type) {
        return poolContext.stream().filter(stockTipDailyMapper -> type.equals(stockTipDailyMapper.getType())).collect(Collectors.toList());
    }
}

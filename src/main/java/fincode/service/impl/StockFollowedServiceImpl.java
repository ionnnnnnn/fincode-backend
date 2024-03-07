package fincode.service.impl;

import fincode.mapper.StockFollowedMapper;
import fincode.mapper.UserMapper;
import fincode.model.*;
import fincode.service.StockFollowedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class StockFollowedServiceImpl implements StockFollowedService {

    @Autowired
    StockFollowedMapper stockFollowedMapper;
    @Autowired
    UserMapper userMapper;

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
    public boolean follow(StockFollowedVO vo, HttpSession session, HttpServletRequest req, HttpServletResponse resp) throws Exception {
        // 无用户信息
        Integer user_id = getUserId(session,req,resp);
        if(user_id==null) return false;
        // 已经关注
        StockFollowedPO po = stockFollowedMapper.search(user_id, vo.getStockId());
        if(po!=null) return true;
        // 插入关注信息
        int lines = stockFollowedMapper.save(new StockFollowedPO(user_id, vo.getStockId()));
        return lines!=0;
    }

    @Override
    public boolean isFollow(StockFollowedVO vo, HttpSession session, HttpServletRequest req, HttpServletResponse resp) {
        Integer user_id = getUserId(session, req, resp);
        if(user_id==null) return false;
        StockFollowedPO po = stockFollowedMapper.search(user_id, vo.getStockId());
        return po!=null;
    }

    @Override
    public boolean unfollow(StockFollowedVO vo, HttpSession session, HttpServletRequest req, HttpServletResponse resp) {
        Integer user_id = getUserId(session,req,resp);
        if(user_id==null) return false;
        int line = stockFollowedMapper.softDelete(new StockFollowedPO(user_id, vo.getStockId()));
        return line==1;
    }

    // TODO: 加入风险报告功能(需要股票详情和stock_tip)
    @Override
    public List<RiskReportVO> listFollowed(StockFollowedListVO vo, HttpSession session, HttpServletRequest req, HttpServletResponse resp) {
        Integer user_id = getUserId(session,req,resp);
        if(user_id!=null){
            List<Integer> stockIds = stockFollowedMapper.findAllByPageForUser(user_id, vo.getPage()-1,vo.getLimit());
            List<RiskReportVO> result=new LinkedList<>();
            for(Integer sid:stockIds){
                // 装填数据
                result.add(new RiskReportVO(sid));
            }
            return result;
        }
        return null;
    }

    @Override
    public Integer getCnt(Integer stock_id) {
        return stockFollowedMapper.getUserCntOf(stock_id);
    }
}

package fincode.service.impl;

import fincode.controller.Stock;
import fincode.mapper.StockFollowedMapper;
import fincode.mapper.StockMapper;
import fincode.mapper.StockRankMapper;
import fincode.mapper.UserMapper;
import fincode.model.*;
import fincode.service.StockService;
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
public class StockServiceImpl implements StockService {
    @Autowired
    StockMapper stockMapper;
    @Autowired
    StockRankMapper stockRankMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    StockFollowedMapper stockFollowedMapper;

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
        if (cookie_username == null || "".equals(cookie_username)) {
            return null;
        }

        // 获取我们登录后存在session中的用户信息，如果为空，表示session已经过期
        Object obj = session.getAttribute("username");


        if (obj == null) {
            return null;
        }

        if(!cookie_username.equals(obj)) return null;
        UserPO user = userMapper.findByPassport(cookie_username);
        if(user==null) return null;
        return user.getId();
    }

    @Override
    public List<StockPO> search(String keyWord) {

        return stockMapper.searchByKeyword(keyWord);
    }

    @Override
    public StockDetailVO getDetail(Integer stockId, String stockCode) {
        if(stockId!=null){
            return new StockDetailVO(stockMapper.searchDetailById(stockId));
        }
        if(stockCode!=null){
            return new StockDetailVO(stockMapper.searchDetailByCode(stockCode));
        }
        return null;
    }

    @Override
    public StockFullInfoVO getFullInfo(Integer stockId) {

        StockDetailPO detail=stockMapper.searchDetailById(stockId);
        if(detail==null){return null;}
        StockPricePO standard=stockMapper.searchPriceById("000001.SZ");
        if(standard==null){return null;}
        StockPricePO price=stockMapper.searchPriceByIdAndTime(detail.getTs_code(),standard.getTime());
        if(price==null){return null;}
        StockFullInfoVO info=new StockFullInfoVO(detail,price);
        IndustryPO industryPO=stockRankMapper.selectIndustryById(detail.getIndustry_id());
        if(industryPO!=null){
            info.setEnname(industryPO.getName());
        }
        return info;
    }

    @Override
    //新增：返回股票是否关注
    public List<StockListVO> listStock(StockFrontListVO vo, HttpSession session, HttpServletRequest req, HttpServletResponse resp) {
        ArrayList<StockListVO> list=new ArrayList<>();
        Integer user_id = getUserId(session,req,resp);
        if(user_id!=null){
            List<StockPO> pos=stockMapper.search(vo.getPage()-1,vo.getLimit());
            for(StockPO po:pos){
                if (stockFollowedMapper.search(user_id,po.getId())!=null){
                    list.add(new StockListVO(po,true));
                }else {
                    list.add(new StockListVO(po,false) );
                }
            }

            return list;
        }
        return null;

    }
}

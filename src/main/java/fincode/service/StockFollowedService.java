package fincode.service;

import fincode.model.ResultVO;
import fincode.model.RiskReportVO;
import fincode.model.StockFollowedListVO;
import fincode.model.StockFollowedVO;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;


public interface StockFollowedService {
    // 关注
    boolean follow(StockFollowedVO vo, HttpSession session, HttpServletRequest req, HttpServletResponse resp) throws Exception;

    // 查询是否关注
    boolean isFollow(StockFollowedVO vo, HttpSession session, HttpServletRequest req, HttpServletResponse resp);

    // 取消关注
    boolean unfollow(StockFollowedVO vo, HttpSession session, HttpServletRequest req, HttpServletResponse resp);

    // 返回用户关注列表，含风险报告
    List<RiskReportVO> listFollowed(StockFollowedListVO vo, HttpSession session, HttpServletRequest req, HttpServletResponse resp);

    // 返回股票的关注用户数量
    Integer getCnt(Integer stopck_id);

}

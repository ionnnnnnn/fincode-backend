package fincode.controller;

import fincode.model.PoolDetail;
import fincode.model.PoolDetailVO;
import fincode.model.PoolList;
import fincode.model.ResultVO;
import fincode.model.req.pool.PoolCreateReq;
import fincode.model.req.pool.PoolDeleteReq;
import fincode.model.req.pool.PoolDetailReq;
import fincode.service.PoolService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author zlj
 * @date 2023/6/7
 */

@RestController
@RequestMapping("/pool")
public class PoolController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private PoolService poolService;

    @PostMapping("/create")
    public ResultVO<PoolDetailVO> createPool(@RequestBody PoolCreateReq poolCreateReq, HttpSession session, HttpServletRequest req, HttpServletResponse resp){
        return poolService.createPool(poolCreateReq, session, req, resp);
    }

    @PostMapping("/delete")
    public ResultVO<?> deletePool(@RequestBody PoolDeleteReq poolDeleteReq, HttpSession session, HttpServletRequest req, HttpServletResponse resp){
        boolean res = poolService.deletePool(poolDeleteReq, session, req, resp);
        if (res) {
            return new ResultVO<>(0, "删除成功");
        }
        else {
            return new ResultVO<>(1, "删除失败");
        }
    }

    @PostMapping("/list")
    public ResultVO<List<PoolList>> showAll(HttpSession session, HttpServletRequest req, HttpServletResponse resp){
        List<PoolList> list = poolService.showAll(session, req, resp);
        if (list == null) {
            return new ResultVO<>(1, "获取失败");
        }
        else {
            return new ResultVO<>(0, "获取成功", list);
        }
    }

    @PostMapping("/detail")
    public ResultVO<PoolDetail> showDetail(@RequestBody PoolDetailReq poolDetailReq, HttpSession session, HttpServletRequest req, HttpServletResponse resp){
        PoolDetail detail = poolService.showDetail(poolDetailReq, session, req, resp);
        if (detail == null) return new ResultVO<>(1, "获取失败");
        else return new ResultVO<>(0, "获取成功", detail);
    }
}

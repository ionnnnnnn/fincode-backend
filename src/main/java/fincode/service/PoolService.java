package fincode.service;

import fincode.model.PoolDetail;
import fincode.model.PoolDetailVO;
import fincode.model.PoolList;
import fincode.model.ResultVO;
import fincode.model.req.pool.PoolCreateReq;
import fincode.model.req.pool.PoolDeleteReq;
import fincode.model.req.pool.PoolDetailReq;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author zlj
 * @date 2023/6/7
 */
public interface PoolService {

    public ResultVO<PoolDetailVO> createPool(PoolCreateReq poolCreateReq, HttpSession session, HttpServletRequest req, HttpServletResponse resp);

    public boolean deletePool(PoolDeleteReq poolDeleteReq, HttpSession session, HttpServletRequest req, HttpServletResponse resp);

    List<PoolList> showAll(HttpSession session, HttpServletRequest req, HttpServletResponse resp);

    PoolDetail showDetail(PoolDetailReq poolDetailReq, HttpSession session, HttpServletRequest req, HttpServletResponse resp);
}

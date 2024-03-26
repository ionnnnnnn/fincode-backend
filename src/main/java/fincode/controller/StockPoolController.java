package fincode.controller;

import fincode.model.ResultVO;
import fincode.model.StockPoolVO;
import fincode.model.req.pool.PoolCreateReq;
import fincode.model.req.pool.PoolDeleteReq;
import fincode.model.req.stockPool.StockPoolCreateReq;
import fincode.model.req.stockPool.StockPoolDeleteReq;
import fincode.service.StockPoolService;
import fincode.service.StockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author lzz
 * @date 2024/3/26
 */
@RestController
@RequestMapping("/stock-pool")
public class StockPoolController {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private StockPoolService stockPoolService;

    // 用户创建股票池
    @PostMapping("/create")
    public ResultVO<?> createPool(@RequestBody StockPoolCreateReq stockPoolCreateReq){
        if(stockPoolService.createPool(stockPoolCreateReq))
            return new ResultVO<>(0, "创建成功");
        else
            return new ResultVO<>(1, "创建失败");
    }

    @PostMapping("/delete")
    public ResultVO<?> deletePool(@RequestBody StockPoolDeleteReq poolDeleteReq){
        boolean res = stockPoolService.deletePool(poolDeleteReq);
        if (res) {
            return new ResultVO<>(0, "删除成功");
        }
        else {
            return new ResultVO<>(1, "删除失败");
        }
    }

    @PostMapping("/pool-list")
    public ResultVO<List<StockPoolVO>> getUserPoolList(@RequestParam Integer userId) {
        return new ResultVO<>(0, "成功", stockPoolService.getUserPoolList(userId));
    }


}

package fincode.controller;

import fincode.model.InvestRecommendRes;
import fincode.model.ResultVO;
import fincode.model.req.investRecommend.InvestRecommendApiListReq;
import fincode.service.InvestRecommendService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zlj
 * @date 2023/3/23
 */

@RestController
@RequestMapping("/investrec")
public class InvestRecommend {

    @Resource
    private InvestRecommendService investRecommendService;

    // @summary 获取投资推荐
    @PostMapping("/list")
    public ResultVO<List<InvestRecommendRes>> list(@RequestBody InvestRecommendApiListReq investRecommendApiListReq) {
        List<InvestRecommendRes> res = investRecommendService.list(investRecommendApiListReq.getPage(), investRecommendApiListReq.getLimit());
        if (res == null) return new ResultVO<>(1,"获取失败");
        else return new ResultVO<>(0,"获取成功", res);
    }
}

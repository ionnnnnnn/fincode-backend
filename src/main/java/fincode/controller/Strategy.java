package fincode.controller;


import com.fasterxml.jackson.databind.util.JSONPObject;
import fincode.model.*;
import fincode.model.req.strategy.StrategyApiGetDetailReq;
import fincode.service.StrategyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/strategy")
public class Strategy {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private StrategyService strategyService;


    // GetDetail @summary 单个策略详情接口
    @PostMapping("/getdetail")
    public ResultVO<StrategyVO> getDetail(@RequestBody StrategyApiGetDetailReq strategyApiGetDetailReq){
        return strategyService.getDetail(strategyApiGetDetailReq.getId());
    }

    // List @summary 返回策略列表接口(无分页)
    @GetMapping("/list")
    public ResultVO<List<StrategyPO>> list(){
        return strategyService.list();
    }

    // ListMatchStock @summary 返回策略适用股票列表
    @PostMapping("/listmatchstock")
    public ResultVO<List<StockTipOverallPO>> listMatchStock(@Validated @RequestBody StrategyFrontListVO strategyFrontListVO, BindingResult result){
        if (result.hasErrors()){
            List<FieldError> fieldErrors = result.getFieldErrors();

            fieldErrors.forEach(fieldError -> {
                logger.error(fieldError.getField() + fieldError.getDefaultMessage());
            });

            String errorMessage = "";
            for (FieldError fieldError : fieldErrors){
                errorMessage += fieldError.getDefaultMessage();
            }
            return new ResultVO<>(1,errorMessage);
        }
        return strategyService.listMatchStock(strategyFrontListVO);
    }
}

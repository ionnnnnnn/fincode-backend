package fincode.controller;

import fincode.model.ResultVO;
import fincode.model.StockRankInfo;
import fincode.model.StockRankVO;
import fincode.service.StockRankService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/stockrank")
public class StockRank {
    @Autowired
    StockRankService stockRankService;
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private ResultVO<String> paramValidation(BindingResult result) {
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
        return null;
    }
    @PostMapping("/list")
    public ResultVO<?> getStockRank(@Validated @RequestBody StockRankVO vo, BindingResult bindingResult){
        ResultVO<String> errors = paramValidation(bindingResult);
        if(errors!=null) {
            return errors;
        }
        List<StockRankInfo> info = stockRankService.getStockRank(vo.getSortType(),vo.getPage(), vo.getLimit());
        if(info!=null) {
            return new ResultVO<>(0, "ok",info);
        }
        return new ResultVO<>(1,"查询超出范围");
    }
}

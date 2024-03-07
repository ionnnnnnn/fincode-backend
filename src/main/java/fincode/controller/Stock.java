package fincode.controller;

import fincode.model.*;
import fincode.model.req.stock.StockApiGetDetailReqById;
import fincode.model.req.stock.StockApiSearchReq;
import fincode.service.StockFollowedService;
import fincode.service.StockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/stock")
public class Stock {
    @Autowired
    StockService stockService;
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
    @PostMapping("/search")
    ResultVO<?> search( @RequestBody StockApiSearchReq stockApiSearchReq){
        String keyWord = stockApiSearchReq.getKeyword();
        if(keyWord==null){
            return new ResultVO<>(1,"err", "错误的输入");
        }

        List<StockPO> result = stockService.search(keyWord);
        if(result!=null) return new ResultVO<>(0,"ok",result);
        else return new ResultVO<>(1,"err", "检索失败");

    }
    @PostMapping("/getdetail")
    ResultVO<?> getDetail( @Validated @RequestBody StockApiGetDetailReqById vo, BindingResult bindingResult){
        ResultVO<String> errors = paramValidation(bindingResult);
        if(errors!=null) return errors;
        StockDetailVO info = stockService.getDetail(vo.getId(),null);
        if(info!=null) return new ResultVO<>(0, "ok",info);
        return new ResultVO<>(1,"查询超出范围");
    }
    @PostMapping("/getfullinfo")
    ResultVO<?> getFullInfo(@Validated @RequestBody StockApiGetDetailReqById vo, BindingResult bindingResult){
        ResultVO<String> errors = paramValidation(bindingResult);
        if(errors!=null) return errors;
        StockFullInfoVO info = stockService.getFullInfo(vo.getId());
        if(info!=null) return new ResultVO<>(0, "ok",info);
        return new ResultVO<>(1,"查询超出范围");
    }
    @PostMapping("/list")
    ResultVO<?> listStock(@Validated @RequestBody StockFrontListVO vo, BindingResult bindingResult, HttpSession session, HttpServletRequest req, HttpServletResponse resp){
        ResultVO<String> errors = paramValidation(bindingResult);
        if(errors!=null) return errors;
        List<StockListVO> result = stockService.listStock(vo,session,req,resp);
        if(result!=null) return new ResultVO<>(0,"ok", result);
        return new ResultVO<>(1,"err","请先登陆");
    }


}

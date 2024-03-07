package fincode.controller;

import fincode.model.*;
import fincode.service.StockFollowedService;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/stockfollowed")
public class StockFollowed {
    @Autowired
    StockFollowedService stockFollowedService;
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
    @PostMapping("/follow")
    ResultVO<String> follow(@Validated @RequestBody StockFollowedVO vo, BindingResult bindingResult, HttpSession session, HttpServletRequest req, HttpServletResponse resp){
        ResultVO<String> errors = paramValidation(bindingResult);
        if(errors!=null) return errors;

        try{
            boolean success = stockFollowedService.follow(vo,session,req,resp);
            if(success) return new ResultVO<>(0,"ok","关注成功");
            else return new ResultVO<>(1,"err", "关注失败");
        }catch (Exception e){
            return new ResultVO<>(1,e.getMessage());
        }
    }

    @PostMapping("/isfollowed")
    ResultVO<?> isfollow(@Validated @RequestBody StockFollowedVO vo, BindingResult bindingResult, HttpSession session, HttpServletRequest req, HttpServletResponse resp){
        ResultVO<String> errors = paramValidation(bindingResult);
        if(errors!=null) return errors;

        boolean success = stockFollowedService.isFollow(vo,session,req,resp);
        if(success) return new ResultVO<>(0,"ok", true);
        else return new ResultVO<>(1,"err",false);
    }

    @PostMapping("/unfollow")
    ResultVO<?> unfollow(@Validated @RequestBody StockFollowedVO vo, BindingResult bindingResult, HttpSession session, HttpServletRequest req, HttpServletResponse resp){
        ResultVO<String> errors = paramValidation(bindingResult);
        if(errors!=null) return errors;

        boolean success = stockFollowedService.unfollow(vo,session,req,resp);
        if(success) return new ResultVO<>(0,"ok", true);
        else return new ResultVO<>(1,"err",false);
    }

    @PostMapping("/getcnt")
    ResultVO<?> getCnt(@Validated @RequestBody StockFollowedVO vo, BindingResult bindingResult, HttpSession session, HttpServletRequest req, HttpServletResponse resp){
        ResultVO<String> errors = paramValidation(bindingResult);
        if(errors!=null) return errors;
        return new ResultVO<>(0,"ok",stockFollowedService.getCnt(vo.getStockId()));
    }

    @PostMapping("/list")
    ResultVO<?> listRiskReportOfUser(@Validated @RequestBody StockFollowedListVO vo, BindingResult bindingResult,HttpSession session, HttpServletRequest req, HttpServletResponse resp){
        ResultVO<String> errors = paramValidation(bindingResult);
        if(errors!=null) return errors;
        List<RiskReportVO> result = stockFollowedService.listFollowed(vo,session,req,resp);
        if(result!=null) return new ResultVO<>(0,"ok", result);
        return new ResultVO<>(1,"err","请先登陆");
    }
}

package fincode.controller.user;


import fincode.model.ResultVO;
import fincode.model.UserVO;
import fincode.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/user")
public class User {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private UserService userService;

    @PostMapping("/signup")
    public ResultVO<UserVO> signUp(@Validated @RequestBody UserVO userVO, BindingResult result){
        ResultVO<UserVO> errorMessage = paramValidation(result);
        if (errorMessage != null) return errorMessage;

        return userService.signUp(userVO);
    }

    private ResultVO<UserVO> paramValidation(BindingResult result) {
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

    @PostMapping("/signin")
    public ResultVO<UserVO> signIn(@Validated @RequestBody UserVO userVO, BindingResult result, HttpSession session, HttpServletRequest request, HttpServletResponse response){
        ResultVO<UserVO> errorMessage = paramValidation(result);
        if (errorMessage != null) return errorMessage;

        return userService.signIn(userVO,session,request,response);
    }

    @GetMapping("/signout")
    public void signOut(HttpSession session, HttpServletRequest request, HttpServletResponse response){
        userService.signOut(session,request,response);
    }

    //判断用户是否登录
    @GetMapping("/issignedin")
    public boolean isSignedIn(@Validated @RequestBody UserVO userVO, BindingResult result, HttpSession session, HttpServletRequest request, HttpServletResponse response){
        ResultVO<UserVO> errorMessage = paramValidation(result);
        if (errorMessage != null) return false;

        return userService.isSignedIn(userVO,session,request,response);
    }

    //检测账号唯一性
    @GetMapping("/checkpassport")
    public boolean checkPassport(@Validated @RequestBody UserVO userVO, BindingResult result){
        ResultVO<UserVO> errorMessage = paramValidation(result);
        if (errorMessage != null) return false;

        return userService.checkPassport(userVO.getPassport());
    }

    //获取用户详细信息
//    @GetMapping("profile")
//    public ResultVO<UserVO> profile()
}

package fincode.service;

import fincode.model.ResultVO;
import fincode.model.UserVO;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public interface UserService {

    //用户注册
    ResultVO<UserVO> signUp(UserVO userVO);

    //用户登录
    ResultVO<UserVO> signIn(UserVO userVO, HttpSession session, HttpServletRequest request, HttpServletResponse response);

    //用户注销
    void signOut(HttpSession session, HttpServletRequest request, HttpServletResponse response);

    //判断用户是否登录
    boolean isSignedIn(UserVO userVO, HttpSession session, HttpServletRequest request, HttpServletResponse response);

    //判断账号唯一性
    boolean checkPassport(String passport);
}

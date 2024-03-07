package fincode.service.impl;

import fincode.mapper.UserMapper;
import fincode.model.ResultVO;
import fincode.model.UserPO;
import fincode.model.UserVO;
import fincode.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private UserMapper userMapper;

    @Override
    public ResultVO<UserVO> signUp(UserVO userVO) {
        // 昵称为非必需参数，默认使用账号名称
        if (userVO.getNickname() == null || "".equals(userVO.getNickname())){
            userVO.setNickname(userVO.getPassport());
        }

        // 账号唯一性数据检查
        if (!checkPassport(userVO.getPassport())){
            logger.error("账号"+userVO.getPassport()+"已经存在");
            return new ResultVO<>(1,"账号"+userVO.getPassport()+"已经存在");
        }

        // 暂不检查昵称唯一性

        UserPO userPO = new UserPO(userVO);

        /*
         * 密码加密
         * 串 + password + 串  --- MD5加密，连续加载三次
         * 盐值  + password  + 盐值  --- 盐值就是一个随机的字符串
         */
//        String oldPassword = userVO.getPassword();
//        //获取盐值
//        String salt = UUID.randomUUID().toString().toUpperCase();
//        //密码盐值加密处理
//        String md5Password = getMD5Password(oldPassword,salt);
//        //加密后从新写入user对象
//        userPO.setPassword(md5Password);
//        userPO.setSalt(salt);

        int insert = userMapper.save(userPO);
        if (insert > 0) {
            logger.info("账号注册成功");
            return new ResultVO<>(0,"账号注册成功",new UserVO(userPO));
        }
        else{
            logger.error("注册时发生错误，请重试");
            return new ResultVO<>(1,"注册时发生错误，请重试");
        }
    }

    @Override
    public ResultVO<UserVO> signIn(UserVO userVO, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        UserPO userSignIn = userMapper.findByPassport(userVO.getPassport());
        if (userSignIn == null) {
            logger.info("账号或密码错误,请重试。");
            return new ResultVO<>(1,"账号或密码错误,请重试。");
        }
        //获取盐值
//        String salt = userSignIn.getSalt();

//        String md5Password = getMD5Password(userVO.getPassword(),salt);

        if (!userVO.getPassword().equals(userSignIn.getPassword())) {
            logger.info("账号或密码错误,请重试。");
            return new ResultVO<>(1,"账号或密码错误,请重试。");
        }

        // 将登录用户信息保存到session中
        session.setAttribute("username", userVO.getPassport());
        // 保存cookie，实现自动登录
        Cookie cookie_username = new Cookie("cookie_username", userVO.getPassport());
        // 设置cookie的持久化时间，30天
        cookie_username.setMaxAge(30 * 24 * 60 * 60);
        // 设置为当前项目下都携带这个cookie
        cookie_username.setPath(request.getContextPath());
        // 向客户端发送cookie
        response.addCookie(cookie_username);

        logger.info("登录成功。");
        return new ResultVO<>(0,"登录成功。",new UserVO(userSignIn));
    }

    @Override
    public void signOut(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        // 删除session里面的用户信息
        session.removeAttribute("username");
        // 保存cookie，实现自动登录
        Cookie cookie_username = new Cookie("cookie_username", "");
        // 设置cookie的持久化时间，0
        cookie_username.setMaxAge(0);
        // 设置为当前项目下都携带这个cookie
        cookie_username.setPath(request.getContextPath());
        // 向客户端发送cookie
        response.addCookie(cookie_username);
    }

    @Override
    public boolean isSignedIn(UserVO userVO, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();

        if (null == cookies) {
            return false;
        }

        // 定义cookie_username，用户的一些登录信息，例如：用户名，密码等
        String cookie_username = null;
        // 获取cookie里面的一些用户信息
        for (Cookie item : cookies) {
            if ("cookie_username".equals(item.getName())) {
                cookie_username = item.getValue();
                break;
            }
        }

        if (cookie_username == null || "".equals(cookie_username)) {
            return false;
        }

        // 获取我们登录后存在session中的用户信息，如果为空，表示session已经过期
        Object obj = session.getAttribute("username");

        if (obj == null) {
            return false;
        }

        return cookie_username.equals(userVO.getPassport()) && obj.equals(userVO.getPassport());
    }


    // 检查账号是否符合规范(目前仅检查唯一性),存在返回false,否则true
    @Override
    public boolean checkPassport(String passport){
        return userMapper.findByPassport(passport) == null;
    }

    /*
     * 功能描述:MD5加密处理
     * @Author zlj
     * @Description
     **/
    private String getMD5Password(String password, String salt){

        String newPassword = "";

        //三次加密
        for (int i = 0; i < 3; i++){
            //md5加密算法的调用
            newPassword = DigestUtils.md5DigestAsHex((salt + password + salt).getBytes()).toUpperCase();
        }

        return newPassword;

    }
}

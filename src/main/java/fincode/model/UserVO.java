package fincode.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UserVO {

    @NotBlank(message = "账号不能为空.")
    @Size(min = 6, max = 16, message = "账号长度在6-16个字符之间.")
    private String passport;

    @NotBlank(message = "密码不能为空")
    @Size(min =6, max = 16, message = "密码长度在6-16个字符之间.")
    private String password;

    private String nickname;

    public UserVO(){}

    public UserVO(@NotNull UserPO userPO){
        this.passport = userPO.getPassport();
        this.password = userPO.getPassword();
        this.nickname = userPO.getNickname();
    }
}

package fincode.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserPO {

    private Integer id;

    private String passport;

    private String password;

//    private String salt;

    private String nickname;

    private String gmt_created;

    private String gmt_modified;

    public UserPO(){}

    public UserPO(UserVO userVO){
        this.passport = userVO.getPassport();
        this.password = userVO.getPassword();
        this.nickname = userVO.getNickname();
    }

}

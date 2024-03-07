package fincode.mapper;

import fincode.model.UserPO;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    @Insert("insert into user values(" +
            "#{userPO.id}, " +
            "#{userPO.passport}, " +
            "#{userPO.password}, " +
            "#{userPO.nickname}, " +
            "#{userPO.gmt_created}, " +
            "#{userPO.gmt_modified})")
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    public int save(@Param("userPO") UserPO userPO);


    @Select("select * from user where passport=#{passport}")
    public UserPO findByPassport(String passport);

    @Select("select * from user where passport=#{passport} and password=#{password}")
    public UserPO findOne(String passport, String password);
}

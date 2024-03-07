package fincode.mapper;

import fincode.model.StockFollowedPO;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface StockFollowedMapper {

    // 插入一条关注信息
    @Insert("insert into stock_followed(user_id, stock_id) values(" +
            "#{po.userId}," +
            "#{po.stockId}" +
            ")")
    public int save(@Param("po") StockFollowedPO po);

    // 查询一条关注信息
    @Select("select * from stock_followed " +
            "where user_id=#{uid} " +
            "and stock_id=#{sid} " +
            "and is_deleted != 1")
    public StockFollowedPO search(@Param("uid") Integer user_id, @Param("sid") Integer stock_id);

    // 删除一条关注信息
    @Delete("update stock_followed " +
            "set is_deleted = 1 " +
            "where user_id = #{po.userId} " +
            "and stock_id = #{po.stockId} " +
            "and is_deleted!=1")
    public int softDelete(@Param("po") StockFollowedPO po);

    // 列举某一用户关注的所有股票id
    @Select("select stock_id " +
            "from stock_followed " +
            "where user_id=#{uid} " +
            "and is_deleted!=1")
    public List<Integer> selectByUid(@Param("uid") Integer user_id);

    // 分页返回用户关注的股票
    @Select("select stock_id " +
            "from stock_followed " +
            "where user_id=#{uid} " +
            "and is_deleted!=1 " +
            "limit #{page}, #{limit}")
    public List<Integer> findAllByPageForUser(@Param("uid") Integer user_id, @Param("page") Integer page, @Param("limit") Integer limit);

    // 返回一个股票的关注用户数量
    @Select("select count(*) " +
            "from stock_followed " +
            "where stock_id=#{sid} " +
            "and is_deleted!=1")
    public Integer getUserCntOf(@Param("sid") Integer stock_id);
}

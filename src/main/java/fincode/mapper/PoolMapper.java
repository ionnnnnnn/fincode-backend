package fincode.mapper;


import fincode.model.PoolDetailPO;
import fincode.model.StockPoolPO;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author zlj
 * @date 2023/6/7
 */

@Mapper
public interface PoolMapper {

    @Insert("insert into stock_pool values(" +
            "#{stockPoolPO.id}, " +
            "#{stockPoolPO.user_id}, " +
            "#{stockPoolPO.pool_name}, " +
            "#{stockPoolPO.condition}, " +
            "#{stockPoolPO.gmt_created}, " +
            "#{stockPoolPO.gmt_modified})")
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    public int createPool(@Param("stockPoolPO") StockPoolPO stockPoolPO);

    @Insert("insert into pool_detail values(" +
            "#{poolDetailPO.id}, " +
            "#{poolDetailPO.pool_id}, " +
            "#{poolDetailPO.stock_id})")
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    public int insertPoolContext(@Param("poolDetailPO")PoolDetailPO poolDetailPO);

    @Delete("delete from stock_pool where user_id=#{user_id} and id=#{pool_id}")
    public int deletePool(int pool_id, int user_id);

    @Select("select * from stock_pool where user_id=#{user_id}")
    List<StockPoolPO> findAll(int user_id);

    @Select("select * from stock_pool where user_id=#{user_id} and id=#{pool_id}")
    StockPoolPO findOne(@Param("user_id") int user_id, @Param("pool_id") int pool_id);

    @Select("select * from pool_detail where pool_id=#{pool_id}")
    List<PoolDetailPO> findAllTips(int pool_id);
}

package fincode.mapper;

import fincode.model.StrategyPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface StrategyMapper {

    @Select("select * from strategy where is_deleted=0 and id=#{id}")
    StrategyPO findOne(int id);

    @Select("select * from strategy where is_deleted=0")
    List<StrategyPO> findAll();

    @Select("select * from strategy where is_deleted=0 and name=#{name}")
    StrategyPO findByName(String name);
}

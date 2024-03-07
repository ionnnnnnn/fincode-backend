package fincode.mapper;

import fincode.model.StockDetailPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author zlj
 * @date 2023/4/15
 */

@Mapper
public interface StockDetailMapper {

    @Select("select * from stock_detail where stock_id=#{stock_id} limit 1")
    StockDetailPO find(int stock_id);
}

package fincode.mapper;

import fincode.model.IndustryPO;
import fincode.model.StockDetailPO;
import fincode.model.StockPricePO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface StockRankMapper {

    // 返回最近的更新时间
    @Select("select time from stock_price " +
            "order by time desc limit 1")
    Integer getLastUpdateDateInStockPrice();

    // 返回最新的排名
    @Select("select * from stock_price " +
            "where time = #{time} " +
            "order by ${sortType}")
    List<StockPricePO> getTodayRank(@Param("time") Integer time, @Param("sortType") String sortType);

    @Select("select * from stock_detail " +
            "where ts_code = #{cid}")
    StockDetailPO selectDetailByTSCode(@Param("cid") String cid);

    @Select("select * from industry " +
            "where id = #{iid}")
    IndustryPO selectIndustryById(@Param("iid") Integer iid);
}

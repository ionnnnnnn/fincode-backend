package fincode.mapper;

import fincode.model.StockDetailPO;
import fincode.model.StockFollowedPO;
import fincode.model.StockPO;
import fincode.model.StockPricePO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface StockMapper {
    @Select("select * from stock " +
            "where is_deleted != 1 " +
            "and name like '%${keyword}%'" +
            "or ts_code like '%${keyword}%'" )
    public List<StockPO> searchByKeyword( @Param("keyword") String keyword);

    @Select("select * from stock " +
            "where is_deleted != 1 "+
            "limit #{page}, #{limit}"
    )
    public List<StockPO> search(@Param("page") Integer page, @Param("limit") Integer limit);

    @Select("select * from stock_detail " +
            "where ts_code=#{code} " +
            "and is_deleted != 1" )
    public StockDetailPO searchDetailByCode(@Param("code") String stock_code);
    @Select("select * from stock_detail " +
            "where stock_id=#{sid} " +
            "and is_deleted != 1")
    public StockDetailPO searchDetailById( @Param("sid") Integer stock_id);

    @Select("select * from stock_price " +
            "where companyId=#{tsCode} " +
            "order by time desc limit 1"
    )
    public StockPricePO searchPriceById(@Param("tsCode") String ts_code);
    @Select("select * from stock_price " +
            "where companyId=#{tsCode} " +
            "and time=#{time} " +
            "order by time desc limit 1"
    )
    public StockPricePO searchPriceByIdAndTime( @Param("tsCode") String ts_code,@Param("time") Integer lastUpdateDate);

    @Select("select * from stock where ts_code=#{ts_code}")
    List<StockPO> findByCode(String ts_code);

    @Select("select * from stock where id=#{stockId}")
    List<StockPO> findById(int stockId);

    @Select("select * from stock where id=#{stockId} limit 100")
    List<StockPO> findByIdLimit100(int stockId);

}

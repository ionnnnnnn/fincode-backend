package fincode.mapper;

import fincode.model.StockTipDailyPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


/**
 * @author zlj
 * @date 2023/3/23
 */

@Mapper
public interface StockTipDailyMapper {

    //分页返回最新的股票数据列表
    @Select("select * from stock_tip_daily where strategy_id=#{strategy_id} and trade_date=#{trade_date} limit #{start}, #{pageSize}")
    List<StockTipDailyPO> findAllLatest(int strategy_id, int trade_date, int start, int pageSize);

    @Select("select * from stock_tip_daily where strategy_id=#{strategy_id} and trade_date between #{startDate} and #{endDate}")
    List<StockTipDailyPO> findAllByDate(int strategy_id, int startDate, int endDate);

    @Select("select * from stock_tip_daily where stock_id=#{stock_id} and strategy_id=#{strategy_id} and trade_date between #{startDate} and #{endDate}")
    List<StockTipDailyPO> findAll(int stock_id, int strategy_id, int startDate, int endDate);

    @Select("select * from stock_tip_daily where stock_id=#{stock_id} and strategy_id=#{strategy_id} and trade_date=#{trade_date}")
    List<StockTipDailyPO> findAllByTradeDate(int stock_id, int strategy_id, int trade_date);

    @Select("select * from stock_tip_daily where stock_id=#{stock_id} and trade_date=#{trade_date}")
    List<StockTipDailyPO> findAll2(int stock_id, int trade_date);

    @Select("select * from stock_tip_daily where id=#{id}")
    StockTipDailyPO findOne(int id);
}

package fincode.mapper;


import fincode.model.StockTipOverallPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface StockTipOverallMapper {

    //根据stockId找出所有股票信息
    @Select("select * from stock_tip_overall where stock_id=#{stock_id}")
    List<StockTipOverallPO> findAllStrategyForOneStock(int stock_id);

    //根据id获取股票信息
    @Select("select * from stock_tip_overall where strategy_id=#{strategy_id} and stock_id=#{stock_id}")
    StockTipOverallPO findAllByStockId(int strategy_id, int stock_id);

    //根据match_rate或profit_rate降序获取股票信息
    @Select("select * from stock_tip_overall where strategy_id=#{strategy_id} order by #{orderByField} desc limit #{start}, #{pageSize}")
    List<StockTipOverallPO> findAllByOrder(int strategy_id, String orderByField, int start, int pageSize);

    //根据stock_id分组查询avg(return_index)
    @Select("select stock_id, avg(return_index) from stock_tip_overall as return_index group by stock_id")
    List<Map<String, Object>> findReturnIndexGroupId();

    @Select("select count(*) from stock_tip_overall where strategy_id=#{strategy_id} and match_rate>0")
    int countMatch(int strategy_id);

    @Select("select count(*) from stock_tip_overall where strategy_id=#{strategy_id} and profit_rate>0")
    int countProfit(int strategy_id);
}

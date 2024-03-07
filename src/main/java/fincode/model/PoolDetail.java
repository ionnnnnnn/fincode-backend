package fincode.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zlj
 * @date 2023/6/7
 */
@Data
public class PoolDetail {
    private Integer pool_id;

    private String pool_name;

    private String strategy_name;

    private String condition;

    private List<PoolTip> tips = new ArrayList<>();

    public void insertPoolTip(StockTipDailyPO stockTipDailyPO, StockDetailPO stockDetailPO){
        PoolTip poolTip = new PoolTip();
        poolTip.setStock_name(stockDetailPO.getName());
        poolTip.setIndustry_id(stockDetailPO.getIndustry_id());
        poolTip.setTs_code(stockDetailPO.getTs_code());
        poolTip.setTrade_date(stockTipDailyPO.getTrade_date());
        poolTip.setType(stockTipDailyPO.getType());
        poolTip.setIs_hit(stockTipDailyPO.getIs_hit());

        tips.add(poolTip);
    }
}

@Data
class PoolTip{
    private String stock_name;

    private String ts_code;

    private Integer industry_id;

    private Integer trade_date;

    private String type;

    private Integer is_hit;
}

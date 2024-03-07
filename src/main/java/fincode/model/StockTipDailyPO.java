package fincode.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author zlj
 * @date 2023/3/23
 */

@Getter
@Setter
public class StockTipDailyPO {

    Integer id;

    Integer stock_id;

    Integer strategy_id;

    String type;

    Integer trade_date;

    Double high;

    Double low;

    Double open;

    Double close;

    Boolean is_deleted;

    String gmt_created;

    String gmt_modified;

    String ext_info;

    Double next_day_open;

    Integer next_day_date;

    Integer is_hit;
}

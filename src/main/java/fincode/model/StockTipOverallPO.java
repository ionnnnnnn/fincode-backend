package fincode.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author zlj
 * @date 2023/3/23
 */

@Getter
@Setter
public class StockTipOverallPO {

    Integer stock_id;

    Integer strategy_id;

//    Integer industry_id;
//
//    Integer day_span;
//
//    Double history_profit_rate;
//
//    Double history_match_rate;
//
//    Double industry_match_rate;
//
//    Double profit_index;
//
//    Boolean is_deleted;
//
//    String gmt_created;
//
//    String gmt_modified;

    Double profit_rate;

    Double match_rate;

    Integer all_count;

    Double return_index;
}

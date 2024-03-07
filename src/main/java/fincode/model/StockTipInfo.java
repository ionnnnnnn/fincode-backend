package fincode.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author zlj
 * @date 2023/4/7
 */
@Getter
@Setter
public class StockTipInfo {
    int stockId;

    List<StockTip> tipList;

    //此处冗余
    List<StockTip> buyTipList;

    List<StockTip> sellTipList;

    double profitRate;

    double matchRate;
}

package fincode.model;

import lombok.Data;

import java.util.List;

/**
 * @author zlj
 * @date 2023/4/13
 */

@Data
public class StockTipLatestInfo {

    private int stockId;

    private List<StockTip> tipList;

    // 此处冗余
    private List<Integer> buyTipStrategyIdList;

    private List<Integer> sellTipStrategyIdList;

    private List<Integer> otherStrategyIdList;
}

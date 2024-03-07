package fincode.model;

import lombok.Data;

/**
 * @author zlj
 * @date 2023/4/13
 */
@Data
public class InvestRecommendRes {

    private StockDetailPO stockDetail;

    private IndustryPO industry;

    private StockTipDailyPO stockTipDaily;

    private StockTipOverallPO stockTipOverall;

    private StockPricePO stockPrice;
}

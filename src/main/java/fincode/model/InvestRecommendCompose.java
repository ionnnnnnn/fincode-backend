package fincode.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zlj
 * @date 2023/4/13
 */

@Data
public class InvestRecommendCompose {

    public StockDetailPO stockDetail;

    public IndustryPO industry;

    public List<StockTipDailyPO> stockTipDaily;

    public StockTipOverallPO stockTipOverall;

    public StockPricePO stockPrice;

    public InvestRecommendCompose(){
        stockDetail = new StockDetailPO();
        industry = new IndustryPO();
        stockTipDaily = new ArrayList<>();
        stockTipOverall = new StockTipOverallPO();
        stockPrice = new StockPricePO();
    }
}

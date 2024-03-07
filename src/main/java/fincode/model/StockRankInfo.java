package fincode.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
public class StockRankInfo {
    private StockDetailPO stockDetailPO;

    private StockPricePO stockPricePO;

    private IndustryPO industryPO;

    public StockRankInfo(StockPricePO stockPricePO, StockDetailPO stockDetailPO) {
        this.stockPricePO=stockPricePO;
        this.stockDetailPO=stockDetailPO;
    }
}

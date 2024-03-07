package fincode.service;

import fincode.model.StockRankInfo;
import java.util.List;

public interface StockRankService {
    List<StockRankInfo> getStockRank(Integer sortType, Integer page, Integer limit);
}

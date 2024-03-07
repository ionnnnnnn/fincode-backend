package fincode.service;

import fincode.model.StockPricePO;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface StockPriceService {
    List<StockPricePO>listPrice(String code, Integer end, Integer limit);
    List<StockPricePO>listPriceById( Integer id, Integer end, Integer limit);
}

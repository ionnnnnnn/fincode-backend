package fincode.service.impl;

import fincode.mapper.StockMapper;
import fincode.mapper.StockPriceMapper;
import fincode.model.StockPricePO;
import fincode.service.StockPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class StockPriceServiceImpl implements StockPriceService {

    @Autowired
    StockPriceMapper stockPriceMapper;
    @Autowired
    StockMapper stockMapper;
    @Override
    public List<StockPricePO> listPrice(String code, Integer end, Integer limit) {
        return stockPriceMapper.searchPrice(code,end,limit);

    }

    @Override
    public List<StockPricePO> listPriceById(Integer id, Integer end, Integer limit) {

        return listPrice(stockMapper.searchDetailById(id).getTs_code(),end,limit);
    }
}

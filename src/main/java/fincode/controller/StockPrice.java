package fincode.controller;

import fincode.model.ResultVO;
import fincode.model.StockPO;
import fincode.model.StockPricePO;
import fincode.model.req.stockPrice.StockPriceApiListPriceByIdReq;
import fincode.service.StockPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/stockprice")
public class StockPrice {
    @Autowired
    StockPriceService stockPriceService;
    @PostMapping("/listprice")
    ResultVO<?> listPrice(@RequestParam String code,@RequestParam Integer end,@RequestParam Integer limit){
        if(code==null||end==null||limit==null){
            return new ResultVO<>(1,"err", "错误的输入");
        }

        List<StockPricePO> result = stockPriceService.listPrice(code,end,limit);
        if(result!=null) return new ResultVO<>(0,"ok",result);
        else return new ResultVO<>(1,"err", "检索失败");

    }
//    @PostMapping("/listpricebyid")
//    ResultVO<?> listPriceById(@RequestParam Integer id,@RequestParam Integer end,@RequestParam Integer limit){
//        if(id==null||end==null||limit==null){
//            return new ResultVO<>(1,"err", "错误的输入");
//        }
//
//        List<StockPricePO> result = stockPriceService.listPriceById(id,end,limit);
//        if(result!=null) return new ResultVO<>(0,"ok",result);
//        else return new ResultVO<>(1,"err", "检索失败");
//
//    }

    @PostMapping("/listpricebyid")
    ResultVO<?> listPriceById(@RequestBody StockPriceApiListPriceByIdReq stockPriceApiListPriceByIdReq) {
        String endDate = stockPriceApiListPriceByIdReq.getEndDate();
        int end = 0;
        if (endDate == null || "".equals(endDate)) {
            LocalDate now = LocalDate.now();
            endDate = now.toString();
        }
        end = Integer.parseInt(endDate.substring(0,4) + endDate.substring(5,7) + endDate.substring(8));
        List<StockPricePO> result = stockPriceService.listPriceById(stockPriceApiListPriceByIdReq.getStockId(),end, stockPriceApiListPriceByIdReq.getLimit());
        if(result!=null) return new ResultVO<>(0,"ok",result);
        else return new ResultVO<>(1,"err", "检索失败");
    }
}

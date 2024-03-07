package fincode.controller;

import fincode.model.*;
import fincode.model.req.stocktip.*;
import fincode.service.StockTipService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

/**
 * @author zlj
 * @date 2023/3/23
 */

@CrossOrigin
@RestController
@RequestMapping("/stocktip")
public class StockTip {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private StockTipService stockTipService;

    // @summary 列出某股票和对应的一批策略给出的投资建议组合
    @PostMapping("/listtips")
    public ResultVO<StockTipInfo> listTips(@Validated @RequestBody StockTipApiListTipsReq stockTipApiListTipsReq, BindingResult result){
        if (result.hasErrors()){
            List<FieldError> fieldErrors = result.getFieldErrors();

            fieldErrors.forEach(fieldError -> {
                logger.error(fieldError.getField() + fieldError.getDefaultMessage());
            });

            String errorMessage = "";
            for (FieldError fieldError : fieldErrors){
                errorMessage += fieldError.getDefaultMessage();
            }
            return new ResultVO<>(1,errorMessage);
        }

        String startDate = stockTipApiListTipsReq.getStartDate();
        String endDate = stockTipApiListTipsReq.getEndDate();

        int start = Integer.parseInt(startDate.substring(0,4) + startDate.substring(5,7) + startDate.substring(8));
        int end = 0;
        if (endDate == null || "".equals(endDate)) {
            LocalDate now = LocalDate.now();
            endDate = now.toString();
        }
        end = Integer.parseInt(endDate.substring(0,4) + endDate.substring(5,7) + endDate.substring(8));

        StockTipInfo stockTipInfo = stockTipService.listTipsByCode(stockTipApiListTipsReq.getStockCode(), stockTipApiListTipsReq.getStrategyIdList(), start,end);
        if (stockTipInfo == null) return new ResultVO<>(1,"获取失败");
        return new ResultVO<>(0,"获取成功", stockTipInfo);
    }

    // @summary 列出某股票和对应的一批策略给出的投资建议组合
    @PostMapping("/listtipsbyid")
    public ResultVO<StockTipInfo> listTipsById(@RequestParam int stockId, @RequestParam List<Integer> strategyIdList, @RequestParam String startDate, @RequestParam String endDate) {
    //public ResultVO<StockTipInfo> listTipsById(@Validated @RequestParam StockTipApiListTipsByIdReq stockTipApiListTipsByIdReq, BindingResult result){
//        if (result.hasErrors()){
//            List<FieldError> fieldErrors = result.getFieldErrors();
//
//            fieldErrors.forEach(fieldError -> {
//                logger.error(fieldError.getField() + fieldError.getDefaultMessage());
//            });
//
//            String errorMessage = "";
//            for (FieldError fieldError : fieldErrors){
//                errorMessage += fieldError.getDefaultMessage();
//            }
//            return new ResultVO<>(1,errorMessage);
//        }

//        String startDate = stockTipApiListTipsByIdReq.getStartDate();
//        String endDate = stockTipApiListTipsByIdReq.getEndDate();

        int start = Integer.parseInt(startDate.substring(0,4) + startDate.substring(5,7) + startDate.substring(8));
        int end = 0;
        if (endDate == null || "".equals(endDate)) {
            LocalDate now = LocalDate.now();
            endDate = now.toString();
        }
        end = Integer.parseInt(endDate.substring(0,4) + endDate.substring(5,7) + endDate.substring(8));

//        StockTipInfo stockTipInfo = stockTipService.listTipsById(stockTipApiListTipsByIdReq.getStockId(), Arrays.asList(stockTipApiListTipsByIdReq.getStrategyIdList()), start, end);
        StockTipInfo stockTipInfo = stockTipService.listTipsById(stockId, strategyIdList, start, end);
        if (stockTipInfo == null) return new ResultVO<>(1,"获取失败");
        return new ResultVO<>(0,"获取成功", stockTipInfo);
    }

    // @summary 列出某股票对应的策略overall列表
    @PostMapping("/listoverall")
    public ResultVO<List<StockTipOverallInfo>> listOverall(@Validated @RequestBody StockTipApiListOverallReq stockTipApiListOverallReq, BindingResult result) {
        if (result.hasErrors()){
            List<FieldError> fieldErrors = result.getFieldErrors();

            fieldErrors.forEach(fieldError -> {
                logger.error(fieldError.getField() + fieldError.getDefaultMessage());
            });

            String errorMessage = "";
            for (FieldError fieldError : fieldErrors){
                errorMessage += fieldError.getDefaultMessage();
            }
            return new ResultVO<>(1,errorMessage);
        }

        List<StockTipOverallInfo> stockTipOverallInfos = stockTipService.listOverallById(stockTipApiListOverallReq.getStockId());

        if (stockTipOverallInfos == null) return new ResultVO<>(1,"获取失败");
        return new ResultVO<>(0,"获取成功", stockTipOverallInfos);
    }


    // @summary 列出某些股票对应某些策略的当日建议列表
    @PostMapping("/listlatest")
    public ResultVO<List<StockTipLatestInfo>> listLatest(@RequestBody StockTipApiListTipsLatestReq stockTipApiListTipsLatestReq) {
        List<StockTipLatestInfo> stockTipLatestInfos = stockTipService.listLatest(stockTipApiListTipsLatestReq.getStockIdList(), stockTipApiListTipsLatestReq.getStrategyIdList());
        return new ResultVO<>(0, "获取成功", stockTipLatestInfos);
    }


    // @summary 列出某股票和某策略在一段时间内的收益率、匹配率
    @PostMapping("/getperiodrate")
    public ResultVO<StockWithStrategyTipInfo> getPeriodRate(@RequestBody StockTipApiPeriodRateReq stockTipApiPeriodRateReq) {
        String startDate = stockTipApiPeriodRateReq.getStartDate();
        String endDate = stockTipApiPeriodRateReq.getEndDate();

        int start = Integer.parseInt(startDate.substring(0,4) + startDate.substring(5,7) + startDate.substring(8));
        int end = 0;
        if (endDate == null || "".equals(endDate)) {
            LocalDate now = LocalDate.now();
            endDate = now.toString();
        }
        end = Integer.parseInt(endDate.substring(0,4) + endDate.substring(5,7) + endDate.substring(8));

        StockWithStrategyTipInfo res = stockTipService.getPeriodRate(stockTipApiPeriodRateReq.getStockId(), stockTipApiPeriodRateReq.getStrategyId(), start, end);
        if (res != null) return new ResultVO<>(0,"success", res);
        return new ResultVO<>(1,"fail");
    }

}

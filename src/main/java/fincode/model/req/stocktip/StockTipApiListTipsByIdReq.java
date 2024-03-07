package fincode.model.req.stocktip;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author zlj
 * @date 2023/4/13
 */

@Data
public class StockTipApiListTipsByIdReq {

    @Min(value = 1, message = "股票Id不能为空")
    int stockId;

    //TODO: "required|min-length:1#策略列表不能为空|策略列表不能为空"
//    List<Integer> strategyIdList;
    Integer[] strategyIdList;

    //TODO: "required|date|date-format:Y-m-d#开始日期不能为空|开始日期必须形如格式：2006-01-02|开始日期必须形如格式：2006-01-02"
    String startDate;

    //TODO: "date|date-format:Y-m-d#结束日期必须形如格式：2006-01-02|结束日期必须形如格式：2006-01-02"
    String endDate;
}

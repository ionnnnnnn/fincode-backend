package fincode.model.req.stocktip;

import lombok.Data;

import java.util.List;

/**
 * @author zlj
 * @date 2023/4/13
 */
@Data
public class StockTipApiListTipsLatestReq {

    //TODO: "required|min-length:1#股票Id列表不能为空|股票Id列表不能为空"
    List<Integer> stockIdList;

    //TODO: "required|min-length:1#策略列表不能为空|策略列表不能为空"
    List<Integer> strategyIdList;

}

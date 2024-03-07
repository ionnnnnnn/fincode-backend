package fincode.model;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class StockRankVO {
    @NotNull(message = "未制定排序类型")
    @Min(value = 0, message = "排序类型错误")
    @Max(value = 3, message = "排序类型错误")
    private Integer sortType;

    @NotNull(message = "分页大小不能为空")
    @Min(value = 5, message = "分页大小应为5，50之间")
    @Max(value = 50, message = "分页大小应为5，50之间")
    Integer limit;

    @NotNull(message = "分页页号不能为空")
    @Min(value = 1, message = "页号最小为1")
    Integer page;

    public StockRankVO() {
    }

    public StockRankVO(Integer sortType, Integer limit, Integer page) {
        this.sortType = sortType;
        this.limit = limit;
        this.page = page;
    }
}

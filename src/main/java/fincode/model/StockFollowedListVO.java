package fincode.model;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class StockFollowedListVO {
    @NotNull(message = "分页大小不能为空")
    @Min(value = 5, message = "分页大小应为5，50之间")
    @Max(value = 50, message = "分页大小应为5，50之间")
    Integer limit;

    @NotNull(message = "分页页号不能为空")
    @Min(value = 1, message = "分页号最小为1")
    Integer page;

    public StockFollowedListVO(Integer page, Integer limit) {
        this.page = page;
        this.limit = limit;
    }
}

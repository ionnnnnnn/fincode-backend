package fincode.model;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.*;

/**
 * @author zlj
 * @date 2023/3/22
 */

@Data
public class StrategyFrontListVO {

    @Min(value = 0, message = "id不能为负")
    int id;

    @NotNull(message = "分页大小不能为空")
    @Range(min = 5, max = 50, message = "分页大小应为5，50之间")
    int limit;

    @NotNull(message = "分页页号不能为空")
    int page;

    @NotBlank(message = "序方式不能为空")
    @Pattern(regexp = "match|profit|latest$")
    String orderBy;

}

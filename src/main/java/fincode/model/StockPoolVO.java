package fincode.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author lzz
 * @date 2024/3/25
 */
@Getter
@Setter
public class StockPoolVO {
    private Integer id;

    private Integer user_id;

    private String pool_name;

    private String condition;
}

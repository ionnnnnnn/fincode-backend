package fincode.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author zlj
 * @date 2023/6/7
 */

@Getter
@Setter
public class StockPoolPO {
    private Integer id;

    private Integer user_id;

    private String pool_name;

    private String condition;

    private String gmt_created;

    private String gmt_modified;

}

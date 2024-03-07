package fincode.model;

import lombok.Data;

/**
 * @author zlj
 * @date 2023/6/7
 */

@Data
public class PoolList {
    int id;
    String pool_name;
    String strategy_name;
    String condition;
    String start;
    String end;
}

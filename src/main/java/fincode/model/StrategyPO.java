package fincode.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StrategyPO {

    private Integer id;

    private String name;

    private String type;

    private Boolean is_deleted;

    private String gmt_created;

    private String gmt_modified;

    private Integer func_enabled;

    public StrategyPO(){}
}

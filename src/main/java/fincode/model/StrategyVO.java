package fincode.model;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class StrategyVO {
    private Integer id;

    private String name;

    private Integer matchCnt;

    private Integer profitCnt;

    private String type;

    public StrategyVO(){}

    public StrategyVO(@NotNull StrategyPO strategyPO){
        this.id = strategyPO.getId();
        this.name = strategyPO.getName();
        this.type = strategyPO.getType();
    }
}

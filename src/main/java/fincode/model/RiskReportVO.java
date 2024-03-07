package fincode.model;

import lombok.Data;

import java.util.List;

@Data
public class RiskReportVO {
    Integer key;
    Integer id;
    String name;
    String code;

    List<RiskReportInnerStrategy> reports;

    public RiskReportVO(Integer id){
        this.id=id;
    }
}

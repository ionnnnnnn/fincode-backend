package fincode.model;

import lombok.Data;
import lombok.NonNull;

import java.time.LocalDateTime;

@Data
public class StockDetailVO {
    Integer id;
    Integer stock_id;
    String name;
    String enname;
    String ts_code;
    String list_date;
    String area;
    Integer industry_id;
    Integer is_deleted;
    LocalDateTime gmt_created;
    LocalDateTime gmt_modified;

    String ext_info;
    public StockDetailVO(){}
    public StockDetailVO(@NonNull StockDetailPO po){
        id= po.getId();
        stock_id= po.getStock_id();
        name=po.getName();
        enname=po.getEnname();
        ts_code=po.getTs_code();
        list_date=po.getList_date();
        area=po.getArea();
        industry_id=po.getIndustry_id();
        is_deleted=po.getIs_deleted();
        gmt_created=po.getGmt_created();
        gmt_modified=po.getGmt_modified();
        ext_info = po.getExt_info();
    }
}

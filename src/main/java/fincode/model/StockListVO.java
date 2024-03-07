package fincode.model;

import fincode.controller.Stock;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDateTime;
@Data
public class StockListVO {
    Integer id;
    Integer stock_id;
    String name;
    String ts_code;
    Integer industry_id;
    Integer is_deleted;
    LocalDateTime gmt_created;
    LocalDateTime gmt_modified;
    boolean followed;
    public StockListVO (){
        followed=false;
    }
    public StockListVO(@NonNull StockPO stock){
        stock_id=stock.getId();
        name=stock.getName();
        ts_code=stock.getTs_code();
        industry_id=stock.getIndustry_id();
        is_deleted=stock.getIs_deleted();
        gmt_created=stock.getGmt_created();
        gmt_modified=stock.getGmt_modified();
        followed=false;
    }
    public StockListVO(@NonNull StockPO stock,boolean followed){
        stock_id=stock.getId();
        name=stock.getName();
        ts_code=stock.getTs_code();
        industry_id=stock.getIndustry_id();
        is_deleted=stock.getIs_deleted();
        gmt_created=stock.getGmt_created();
        gmt_modified=stock.getGmt_modified();
        this.followed=followed;
    }
}

package fincode.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockDetailPO {
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStock_id() {
        return stock_id;
    }

    public void setStock_id(Integer stock_id) {
        this.stock_id = stock_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEnname() {
        return enname;
    }

    public void setEnname(String enname) {
        this.enname = enname;
    }

    public String getTs_code() {
        return ts_code;
    }

    public LocalDateTime getGmt_modified() {
        return gmt_modified;
    }

    public Integer getIs_deleted() {
        return is_deleted;
    }

    public LocalDateTime getGmt_created() {
        return gmt_created;
    }

    public Integer getIndustry_id() {
        return industry_id;
    }

    public String getArea() {
        return area;
    }

    public String getExt_info() {
        return ext_info;
    }

    public String getList_date() {
        return list_date;
    }
}

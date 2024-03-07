package fincode.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockPO {
    Integer id;

    String name;
    String ts_code;
    Integer industry_id;
    Integer is_deleted;
    LocalDateTime gmt_created;
    LocalDateTime gmt_modified;

    public String getName() {
        return name;
    }



    public Integer getId() {
        return id;
    }

    public Integer getIndustry_id() {
        return industry_id;
    }

    public LocalDateTime getGmt_created() {
        return gmt_created;
    }

    public Integer getIs_deleted() {
        return is_deleted;
    }

    public LocalDateTime getGmt_modified() {
        return gmt_modified;
    }

    public String getTs_code() {
        return ts_code;
    }
}

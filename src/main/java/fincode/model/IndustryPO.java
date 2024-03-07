package fincode.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.tomcat.jni.Local;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IndustryPO {
    Integer id;
    String name;
    Integer is_deleted;
    LocalDateTime gmt_created;
    LocalDateTime gmt_modified;
}

package fincode.mapper;

import fincode.model.IndustryPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author zlj
 * @date 2023/4/15
 */

@Mapper
public interface IndustryMapper {

    @Select("select * from industry where id=#{industry_id}")
    IndustryPO find(int industry_id);
}

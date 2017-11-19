package com.liyuan.ems.dao.condition;

import com.liyuan.ems.model.condition.ConditionItemDTO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * Created by tidus on 2017/9/12.
 */
public interface ConditionConfigMapper {


    /**
     * 根据configCode查询条件
     * @param configCode
     * @return
     */
    List<ConditionItemDTO> getConditions(@Param("configCode") String configCode);

    @Select("<script>" +
            "${paramSql} " +
            "</script>")
    @ResultType(Map.class)
    List<Map> getConditionKeyValue(@Param("paramSql") String sql);


}

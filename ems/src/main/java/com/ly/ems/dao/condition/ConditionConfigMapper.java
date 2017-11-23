package com.ly.ems.dao.condition;

import com.ly.ems.model.condition.ConditionItem;
import com.ly.ems.model.condition.ConditionItemDTO;
import com.ly.ems.model.condition.constants.ConditionType;
import org.apache.ibatis.annotations.Param;
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

    /**
     * 根据conditionCode查询条件
     * @param conditionCode
     * @return
     */
    ConditionItemDTO getConditionItemByCode(@Param("conditionCode") String conditionCode);

    /**
     * 根据conditionCode查询条件
     * @param conditionCode
     * @return
     */
    ConditionItemDTO getConditionItemByCodeAndType(@Param("conditionCode") String conditionCode, @Param("conditionType") ConditionType conditionType);

    /**
     * 执行sql
     * @param sql
     * @return
     */
    List<Map> getConditionKeyValue(@Param("paramSql") String sql);


}

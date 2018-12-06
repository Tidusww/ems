package com.ly.ems.service.condition;
import com.ly.ems.core.datasource.DetermineDataSource;
import com.ly.ems.core.datasource.MultipleRoutingDataSource;
import com.ly.ems.model.condition.ConditionItemDTO;

import java.util.List;
import java.util.Map;

/**
 * Created by tidus on 2017/9/13.
 */
@DetermineDataSource(MultipleRoutingDataSource.DATA_SOURCE_EMS)
public interface ConditionConfigService {

    List<ConditionItemDTO> getConditions(String configCode);

    ConditionItemDTO getSelectItem(String conditionCode);

    /**
     * 根据条件所带的Sql查询 key value
     * @param conditionSql
     */
    @DetermineDataSource(MultipleRoutingDataSource.DATA_SOURCE_NOT_CONTROL)
    List<Map> getConditionDTOKeyValueBySql(String conditionCode, String conditionSql);

    /**
     * 根据条件对应的ENUM查询 key value
     * @param conditionItemDTO
     */
    void getConditionKeyValueByEnum(ConditionItemDTO conditionItemDTO);

    /**
     * 将通过Sql或者Enum返回的keyValues放到DTO中
     * @param conditionItemDTO
     * @param keyValues
     */
    void setConditionItemKeyValues(ConditionItemDTO conditionItemDTO, List<Map> keyValues);
}

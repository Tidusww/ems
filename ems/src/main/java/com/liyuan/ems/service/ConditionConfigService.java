package com.liyuan.ems.service;
import com.liyuan.ems.core.datasource.DetermineDataSource;
import com.liyuan.ems.core.datasource.MultipleRoutingDataSource;
import com.liyuan.ems.model.condition.ConditionItemDTO;

import java.util.List;

/**
 * Created by tidus on 2017/9/13.
 */
@DetermineDataSource(MultipleRoutingDataSource.DATA_SOURCE_EMS)
public interface ConditionConfigService {

    List<ConditionItemDTO> getConditions(String configCode);

    @DetermineDataSource(MultipleRoutingDataSource.DATA_SOURCE_NOT_CONTROL)
    void setConditionDTOKeyValue(ConditionItemDTO conditionItemDTO);
}

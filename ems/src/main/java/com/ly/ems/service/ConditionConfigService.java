package com.ly.ems.service;
import com.ly.ems.core.datasource.DetermineDataSource;
import com.ly.ems.core.datasource.MultipleRoutingDataSource;
import com.ly.ems.model.condition.ConditionItemDTO;
import com.ly.ems.core.datasource.DetermineDataSource;

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

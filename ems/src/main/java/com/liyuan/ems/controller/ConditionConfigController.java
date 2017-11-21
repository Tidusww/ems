package com.liyuan.ems.controller;

import com.liyuan.ems.common.utils.AjaxResult;
import com.liyuan.ems.core.datasource.MultipleRoutingDataSource;
import com.liyuan.ems.model.condition.ConditionItemDTO;
import com.liyuan.ems.model.condition.constants.ConditionDatasource;
import com.liyuan.ems.model.condition.constants.ConditionType;
import com.liyuan.ems.service.ConditionConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tidus on 2017/9/13.
 */
@Controller
@RequestMapping("/conditionConfig")
public class ConditionConfigController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConditionConfigController.class);

    private HashMap<ConditionDatasource, String> datasourceMapper = new HashMap(){
        {
            put(ConditionDatasource.EMS, MultipleRoutingDataSource.DATA_SOURCE_EMS);
        }
    };

    @Autowired
    private ConditionConfigService conditionConfigService;

    @ResponseBody
    @RequestMapping(value = "/getConditions")
    public AjaxResult getConditions(HttpServletRequest request,
                                    Model model,
                                    @RequestParam(name = "configCode", required = false, defaultValue = "") String configCode) {
        List<ConditionItemDTO> conditionItemDTOs = null;
        try {
            conditionItemDTOs = conditionConfigService.getConditions(configCode);
        }catch (Exception ex){
            LOGGER.info(String.format("查询条件失败: configCode:[%s]", configCode));
            AjaxResult.fail(String.format("查询条件失败: configCode:[%s]", configCode));
        }

        if(conditionItemDTOs == null || conditionItemDTOs.isEmpty()){
            AjaxResult.success(conditionItemDTOs);
        }

        //处理下拉框键值对
        for (int i = 0; i < conditionItemDTOs.size(); i++) {
            ConditionItemDTO conditionItemDTO = conditionItemDTOs.get(i);
            if (conditionItemDTO.getConditionType() != ConditionType.SELECT
                    || StringUtils.isEmpty(conditionItemDTO.getConditionSql())) {
                continue;
            }

            //切换数据源
            ConditionDatasource conditionDatasource = conditionItemDTO.getConditionDatasource();
            String datasource = datasourceMapper.get(conditionDatasource);
            if(!StringUtils.isEmpty(datasource)){
                MultipleRoutingDataSource.setDataSourceKey(datasource);
            }else{
                MultipleRoutingDataSource.setDataSourceKey(MultipleRoutingDataSource.DATA_SOURCE_EMS);
            }

            //查找键值
            conditionConfigService.setConditionDTOKeyValue(conditionItemDTO);
        }

        return AjaxResult.success(conditionItemDTOs);
    }

}

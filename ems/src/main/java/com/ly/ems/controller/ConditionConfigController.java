package com.ly.ems.controller;

import com.ly.ems.common.utils.AjaxResult;
import com.ly.ems.core.datasource.MultipleRoutingDataSource;
import com.ly.ems.model.condition.ConditionItemDTO;
import com.ly.ems.model.condition.constants.ConditionDatasource;
import com.ly.ems.model.condition.constants.ConditionType;
import com.ly.ems.service.condition.ConditionConfigService;
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
    @RequestMapping(value = "/getConditions", name="获取ConfigCode下所有条件")
    public AjaxResult getConditions(HttpServletRequest request, Model model,
                                    @RequestParam(name = "configCode") String configCode) {
        List<ConditionItemDTO> conditionItemDTOs = null;
        try {
            conditionItemDTOs = conditionConfigService.getConditions(configCode);
        }catch (Exception ex){
            ex.printStackTrace();
            LOGGER.info(String.format("查询条件失败: configCode:[%s]", configCode));
            return AjaxResult.fail(String.format("查询条件失败: configCode:[%s]", configCode));
        }

        if(conditionItemDTOs == null || conditionItemDTOs.isEmpty()){
            return AjaxResult.success(conditionItemDTOs);
        }

        //处理下拉框键值对
        for (int i = 0; i < conditionItemDTOs.size(); i++) {
            ConditionItemDTO conditionItemDTO = conditionItemDTOs.get(i);
            if (conditionItemDTO.getConditionType() == ConditionType.SELECT) {
                this.getConditionItemKeyValues(conditionItemDTO);
            }
        }

        return AjaxResult.success(conditionItemDTOs);
    }

    @ResponseBody
    @RequestMapping(value = "/getSelectItem", name = "获取下拉框类型的条件项")
    public AjaxResult getSelectItem(HttpServletRequest request, Model model,
                                    @RequestParam(name = "conditionCode") String conditionCode) {
        ConditionItemDTO conditionItemDTO = null;
        try {
            conditionItemDTO = conditionConfigService.getSelectItem(conditionCode);
        }catch (Exception ex){
            ex.printStackTrace();
            LOGGER.info(String.format("查询下拉框数据失败: configCode:[%s]", conditionCode));
            return AjaxResult.fail(String.format("查询下拉框数据失败: configCode:[%s]", conditionCode));
        }

        if(conditionItemDTO == null){
            return AjaxResult.success(conditionItemDTO);
        }

        //处理下拉框键值对
        this.getConditionItemKeyValues(conditionItemDTO);

        return AjaxResult.success(conditionItemDTO);
    }

    private void getConditionItemKeyValues(ConditionItemDTO conditionItemDTO) {
        //根据SQL找
        if( !StringUtils.isEmpty(conditionItemDTO.getConditionSql()) ){
            //切换数据源
            ConditionDatasource conditionDatasource = conditionItemDTO.getConditionDatasource();
            String datasource = datasourceMapper.get(conditionDatasource);
            if(!StringUtils.isEmpty(datasource)){
                MultipleRoutingDataSource.setDataSourceKey(datasource);
            }else{
                MultipleRoutingDataSource.setDataSourceKey(MultipleRoutingDataSource.DATA_SOURCE_EMS);
            }
            //查找键值
            conditionConfigService.getConditionDTOKeyValueBySql(conditionItemDTO);
        }

        //根据Enum找
        if ((conditionItemDTO.getKeyValueMaps() == null || conditionItemDTO.getValueMaps() == null)
                && !StringUtils.isEmpty(conditionItemDTO.getConditionEnum())) {
            conditionConfigService.getConditionKeyValueByEnum(conditionItemDTO);
        }

        //无论找不找到都清空相关信息
        conditionItemDTO.setConditionSql("");
        conditionItemDTO.setConditionEnum("");
    }

}

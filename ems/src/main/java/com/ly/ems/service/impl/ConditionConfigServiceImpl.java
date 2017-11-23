package com.ly.ems.service.impl;

import com.ly.ems.dao.condition.ConditionConfigMapper;
import com.ly.ems.model.condition.ConditionItemDTO;
import com.ly.ems.model.condition.constants.ConditionType;
import com.ly.ems.service.ConditionConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tidus on 2017/9/13.
 */
@Service
public class ConditionConfigServiceImpl implements ConditionConfigService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConditionConfigServiceImpl.class);

    @Autowired
    ConditionConfigMapper conditionConfigMapper;

    @Override
    public List<ConditionItemDTO> getConditions(String configCode){
        //获取条件配置
        List<ConditionItemDTO> conditionDTOs = conditionConfigMapper.getConditions(configCode);
        return conditionDTOs;
    }

    @Override
    public ConditionItemDTO getSelectItem(String conditionCode) {
        ConditionItemDTO conditionItemDTO = conditionConfigMapper.getConditionItemByCodeAndType(conditionCode, ConditionType.SELECT);
        return conditionItemDTO;
    }

    @Override
    public void setConditionDTOKeyValue(ConditionItemDTO conditionItemDTO) {
        String conditionSql = conditionItemDTO.getConditionSql();
        try {
            //执行Sql
            List<Map> keyValues = conditionConfigMapper.getConditionKeyValue(conditionSql);
            if(keyValues != null && keyValues.size() > 0){
                List<Map> valueMaps = new ArrayList<Map>();
                for (int j = 0; j < keyValues.size(); j++) {
                    Map<String, Object> keyValue = keyValues.get(j);
                    Map<String, Object> valueMap = new HashMap<String, Object>();
                    valueMap.put(keyValue.get("key")+"", keyValue.get("value")+"");
                    valueMaps.add(valueMap);
                }
                conditionItemDTO.setKeyValueMaps(keyValues);    //{key:`1`, value:`aa`}
                conditionItemDTO.setValueMaps(valueMaps);       //{`1`: `aa`}
            }
        }catch (Exception ex){
            //执行Sql出错
            ex.printStackTrace();
        }finally {
            conditionItemDTO.setConditionSql("");
        }

    }

}

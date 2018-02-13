package com.ly.ems.service.impl;

import com.ly.ems.core.mybatis.BaseCodeValueEnum;
import com.ly.ems.core.mybatis.BaseKeyValueEnum;
import com.ly.ems.dao.condition.ConditionConfigMapper;
import com.ly.ems.model.condition.ConditionItemDTO;
import com.ly.ems.model.condition.constants.ConditionType;
import com.ly.ems.service.ConditionConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tidus on 2017/9/13.
 */
@Service
@CacheConfig(cacheNames = "conditionCache",keyGenerator="keyGenerator")
public class ConditionConfigServiceImpl implements ConditionConfigService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConditionConfigServiceImpl.class);

    @Autowired
    ConditionConfigMapper conditionConfigMapper;

    @Cacheable
    @Override
    public List<ConditionItemDTO> getConditions(String configCode){
        //获取条件配置
        List<ConditionItemDTO> conditionDTOs = conditionConfigMapper.getConditions(configCode);
        return conditionDTOs;
    }

    @Cacheable
    @Override
    public ConditionItemDTO getSelectItem(String conditionCode) {
        ConditionItemDTO conditionItemDTO = conditionConfigMapper.getConditionItemByCodeAndType(conditionCode, ConditionType.SELECT);
        return conditionItemDTO;
    }

    @Cacheable
    @Override
    public void getConditionDTOKeyValueBySql(ConditionItemDTO conditionItemDTO) {
        String conditionSql = conditionItemDTO.getConditionSql();
        try {
            //通过Sql查询下拉项并转换为keyValues
            List<Map> keyValues = conditionConfigMapper.getConditionKeyValue(conditionSql);
            this.setConditionItemKeyValues(conditionItemDTO, keyValues);
        }catch (Exception ex){
            //执行Sql出错
            ex.printStackTrace();
            LOGGER.error(String.format("查询条件Sql出错, conditionCode:[%s]", conditionItemDTO.getConditionCode()));
        }
    }


    @Override
    public void getConditionKeyValueByEnum(ConditionItemDTO conditionItemDTO) {
        String enumPath = conditionItemDTO.getConditionEnum();
        try {
            //通过Enum查询下拉项并转换为keyValues
            List<Map> keyValues = null;

            Class<?> Clazz = Class.forName(enumPath);
            if (BaseKeyValueEnum.class.isAssignableFrom(Clazz)) {
                Class<BaseKeyValueEnum> KeyValueEnum = (Class<BaseKeyValueEnum>) Clazz;
                keyValues = this.getKeyValuesFromKeyValueEnum(KeyValueEnum);
            } else if (BaseCodeValueEnum.class.isAssignableFrom(Clazz)) {
                Class<BaseCodeValueEnum> CodeValueEnum = (Class<BaseCodeValueEnum>) Clazz;
                keyValues = this.getKeyValuesFromCodeValueEnum(CodeValueEnum);
            } else {
                throw new RuntimeException("不支持的枚举类型:"+Clazz.getName());
            }

            this.setConditionItemKeyValues(conditionItemDTO, keyValues);

        }catch (Exception ex){
            //枚举出错
            ex.printStackTrace();
            LOGGER.error(String.format("查询条件Enum出错, conditionCode:[%s]", conditionItemDTO.getConditionCode()));
        }
    }

    /**
     * 将通过Sql或者Enum返回的keyValues放到DTO中
     * @param conditionItemDTO
     * @param keyValues
     */
    @Transactional(readOnly = true)
    private void setConditionItemKeyValues(ConditionItemDTO conditionItemDTO, List<Map> keyValues){
        if(keyValues != null && keyValues.size() > 0){
            List<Map> valueMaps = new ArrayList<Map>();
            for (int j = 0; j < keyValues.size(); j++) {
                Map keyValue = keyValues.get(j);
                Map valueMap = new HashMap<String, String>();
                valueMap.put(keyValue.get("key")+"", keyValue.get("value")+"");
                valueMaps.add(valueMap);
            }
            conditionItemDTO.setKeyValueMaps(keyValues);    //{key:`1`, value:`aa`}
            conditionItemDTO.setValueMaps(valueMaps);       //{`1`: `aa`}
        }
    }

    /**
     * 将BaseKeyValueEnum类型的枚举转换成Map返回
     * @param Clazz
     * @return
     */
    @Transactional(readOnly = true)
    private List<Map> getKeyValuesFromKeyValueEnum(Class<BaseKeyValueEnum> Clazz) {
        List<Map> keyValues = new ArrayList<Map>();
        BaseKeyValueEnum[] keyValueEnums = Clazz.getEnumConstants();
        for (int i = 0; i < keyValueEnums.length; i++) {
            final BaseKeyValueEnum keyValueEnum = keyValueEnums[i];
            Map<String, String> keyValue = new HashMap<String, String>(){
                {
                    put("key", keyValueEnum.getKey().toString());
                    put("value", keyValueEnum.getValue());
                }
            };
            keyValues.add(keyValue);
        }
        return keyValues;
    }

    /**
     * 将BaseCodeValueEnum类型的枚举转换成Map返回
     * @param Clazz
     * @return
     */
    @Transactional(readOnly = true)
    private List<Map> getKeyValuesFromCodeValueEnum(Class<BaseCodeValueEnum> Clazz) {
        List<Map> keyValues = new ArrayList<Map>();
        BaseCodeValueEnum[] codeValueEnums = Clazz.getEnumConstants();
        for (int i = 0; i < codeValueEnums.length; i++) {
            final BaseCodeValueEnum codeValueEnum = codeValueEnums[i];
            Map<String, String> keyValue = new HashMap<String, String>(){
                {
                    put("key", codeValueEnum.getCode());
                    put("value", codeValueEnum.getValue());
                }
            };
            keyValues.add(keyValue);
        }
        return keyValues;
    }
}

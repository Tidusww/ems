package com.ly.ems.model.condition;

import java.util.List;
import java.util.Map;

/**
 * Created by tidus on 2017/9/13.
 */
public class ConditionItemDTO extends ConditionItem{

    private List<Map<String, String>> keyValueMaps;
    private List<Map<String, String>> valueMaps;

    public List<Map<String, String>> getKeyValueMaps() {
        return keyValueMaps;
    }

    public void setKeyValueMaps(List<Map<String, String>> keyValueMaps) {
        this.keyValueMaps = keyValueMaps;
    }

    public List<Map<String, String>> getValueMaps() {
        return valueMaps;
    }

    public void setValueMaps(List<Map<String, String>> conditionKeyValues) {
        this.valueMaps = conditionKeyValues;
    }


}

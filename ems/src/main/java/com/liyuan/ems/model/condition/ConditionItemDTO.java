package com.liyuan.ems.model.condition;

import java.util.List;
import java.util.Map;

/**
 * Created by tidus on 2017/9/13.
 */
public class ConditionItemDTO extends ConditionItem{

    private List<Map> keyValueMaps;
    private List<Map> valueMaps;

    public List<Map> getKeyValueMaps() {
        return keyValueMaps;
    }

    public void setKeyValueMaps(List<Map> keyValueMaps) {
        this.keyValueMaps = keyValueMaps;
    }

    public List<Map> getValueMaps() {
        return valueMaps;
    }

    public void setValueMaps(List<Map> conditionKeyValues) {
        this.valueMaps = conditionKeyValues;
    }


}

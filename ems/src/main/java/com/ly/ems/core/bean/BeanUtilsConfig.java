package com.ly.ems.core.bean;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.springframework.beans.factory.InitializingBean;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by tidus on 2018/10/29.
 */
public class BeanUtilsConfig implements InitializingBean {

    private Map converters = new HashMap();

    public void afterPropertiesSet() throws Exception {

        Set keys = converters.keySet();

        for (Object key : keys) {
            ConvertUtils.register((Converter) converters.get(key), Class.forName((String) key));
        }
    }

    public Map getConverters() {
        return converters;
    }

    public void setConverters(Map converterMap) {
        this.converters = converterMap;
    }
}
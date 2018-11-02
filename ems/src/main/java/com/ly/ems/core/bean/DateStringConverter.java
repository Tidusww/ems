package com.ly.ems.core.bean;

import com.ly.ems.common.utils.DateUtil;
import com.ly.ems.common.utils.EnumUtil;
import com.ly.ems.core.mybatis.BaseCodeValueEnum;
import com.ly.ems.core.mybatis.BaseKeyValueEnum;
import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.Converter;

import java.util.Date;

/**
 * DateStringConverter
 */
public class DateStringConverter implements Converter {

    @Override
    public Object convert(Class type, Object value) {

        if (value == null) {
            throw new ConversionException("No value specified");
        }

        if (type == null) {
            throw new ConversionException("No type specified");
        }

        if (!(value instanceof String)) {
            throw new ConversionException("value should be String");
        }

        if (Date.class.isAssignableFrom(type)) {
            return DateUtil.parseDate((String) value);
        }

        return value;
    }
}

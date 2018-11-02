package com.ly.ems.core.bean;

import com.ly.ems.common.utils.EnumUtil;
import com.ly.ems.core.mybatis.BaseCodeValueEnum;
import com.ly.ems.core.mybatis.BaseKeyValueEnum;
import com.ly.ems.model.base.employee.constant.GenderEnum;
import com.ly.ems.model.base.employee.constant.SalaryBankEnum;
import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.collections.KeyValue;

/**
 * BaseEnumConverter
 */
public class BaseEnumConverter implements Converter {

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

        if (BaseKeyValueEnum.class.isAssignableFrom(type)) {
            return EnumUtil.getKeyValueEnumByValue(type, (String) value);
        }

        if (BaseCodeValueEnum.class.isAssignableFrom(type)) {
            return EnumUtil.getCodeValueEnumByValue(type, (String) value);
        }

        return value;
    }
}

package com.ly.ems.core.springmvc.conversion.enumerate;

import com.ly.ems.common.utils.EnumUtil;
import com.ly.ems.core.mybatis.BaseKeyValueEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;

/**
 * Created by tidus on 2017/11/3.
 */

/**
 * BaseKeyValueEnum 枚举的Converter
 *
 * @param <T>
 */
public class KeyValueEnumConverter<T extends Enum<?> & BaseKeyValueEnum> implements Converter<String, T> {

    private final Class<T> enumType;

    public KeyValueEnumConverter(Class<T> enumType) {
        this.enumType = enumType;
    }

    @Override
    public T convert(String source) {
        if (StringUtils.isEmpty(source)) {
            return null;
        }
        return EnumUtil.getKeyValueEnumByKey(this.enumType, Integer.parseInt(source));
    }
}

package com.liyuan.ems.core.springmvc.conversion;

import org.springframework.core.convert.converter.Converter;

/**
 * 默认的EnumConverter
 */

/**
 * Created by tidus on 2017/11/3.
 */
public class CommonEnumConverter<T extends Enum> implements Converter<String, T> {

    private final Class<T> enumType;

    public CommonEnumConverter(Class<T> enumType) {
        this.enumType = enumType;
    }

    public T convert(String source) {
        if (source.length() == 0) {
            return null;
        }
        return (T) Enum.valueOf(this.enumType, source.trim());
    }
}
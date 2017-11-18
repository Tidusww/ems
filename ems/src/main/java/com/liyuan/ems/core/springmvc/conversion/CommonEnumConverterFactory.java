package com.liyuan.ems.core.springmvc.conversion;

import com.liyuan.ems.core.mybatis.BaseCodeValueEnum;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

/**
 * Created by tidus on 2017/11/3.
 */
final class CommonEnumConverterFactory implements ConverterFactory<String, Enum> {

    public <T extends Enum> Converter<String, T> getConverter(Class<T> targetType) {
        if (BaseCodeValueEnum.class.isAssignableFrom(targetType)) {
            return new CodeValueEnumConverter(targetType);
        } else {
            return new CommonEnumConverter(targetType);
        }
    }

}

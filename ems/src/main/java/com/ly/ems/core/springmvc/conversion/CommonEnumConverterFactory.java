package com.ly.ems.core.springmvc.conversion;

import com.ly.ems.core.mybatis.BaseCodeValueEnum;
import com.ly.ems.core.mybatis.BaseKeyValueEnum;
import com.ly.ems.core.mybatis.BaseCodeValueEnum;
import com.ly.ems.core.mybatis.BaseKeyValueEnum;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

/**
 * Created by tidus on 2017/11/3.
 */
final class CommonEnumConverterFactory implements ConverterFactory<String, Enum> {

    /**
     * 根据targetType的类型返回对应的converter
     * @param targetType
     * @param <T>
     * @return
     */
    public <T extends Enum> Converter<String, T> getConverter(Class<T> targetType) {
        if (BaseCodeValueEnum.class.isAssignableFrom(targetType)) {
            return new CodeValueEnumConverter(targetType);
        } else if (BaseKeyValueEnum.class.isAssignableFrom(targetType)) {
            return new KeyValueEnumConverter(targetType);
        } else {
            return new CommonEnumConverter(targetType);
        }
    }
}

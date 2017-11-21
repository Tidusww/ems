package com.ly.ems.core.springmvc.conversion;

import com.ly.ems.common.utils.EnumUtil;
import com.ly.ems.core.mybatis.BaseCodeValueEnum;
import com.ly.ems.common.utils.EnumUtil;
import com.ly.ems.core.mybatis.BaseCodeValueEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;

/**
 * Created by tidus on 2017/11/3.
 */

/**
 * BaseCodeValueEnum 枚举的Converter
 *
 * @param <T>
 */
public class CodeValueEnumConverter<T extends Enum<?> & BaseCodeValueEnum> implements Converter<String, T> {

    private final Class<T> enumType;

    public CodeValueEnumConverter(Class<T> enumType) {
        this.enumType = enumType;
    }

    @Override
    public T convert(String source) {
        if (StringUtils.isEmpty(source)) {
            return null;
        }
        return EnumUtil.getCodeValueEnumByCode(this.enumType, source);
    }
}

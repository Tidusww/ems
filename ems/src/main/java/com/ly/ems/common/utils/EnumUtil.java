package com.ly.ems.common.utils;


import com.ly.ems.core.mybatis.BaseCodeValueEnum;
import com.ly.ems.core.mybatis.BaseKeyValueEnum;
import com.ly.ems.core.mybatis.BaseCodeValueEnum;

/**
 * Created by tidus on 2017/10/31.
 */
public class EnumUtil {

    public static <E extends Enum<?> & BaseCodeValueEnum> E getCodeValueEnumByCode(Class<E> enumClass, String code) {
        E[] enumConstants = enumClass.getEnumConstants();
        for (E e : enumConstants) {
            if (e.getCode().equals(code)) {
                return e;
            }
        }
        return null;
    }

    public static <E extends Enum<?> & BaseKeyValueEnum> E getKeyValueEnumByKey(Class<E> enumClass, Integer key) {
        E[] enumConstants = enumClass.getEnumConstants();
        for (E e : enumConstants) {
            if (e.getKey().equals(key)) {
                return e;
            }
        }
        return null;
    }
}

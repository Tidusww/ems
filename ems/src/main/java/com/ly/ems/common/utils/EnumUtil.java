package com.ly.ems.common.utils;


import com.ly.ems.core.mybatis.BaseCodeValueEnum;
import com.ly.ems.core.mybatis.BaseKeyValueEnum;
import com.ly.ems.core.mybatis.BaseCodeValueEnum;

/**
 * Created by tidus on 2017/10/31.
 */
public class EnumUtil {

    /**
     * 根据code返回BaseCodeValueEnum类型的Enum
     * @param enumClass
     * @param code
     * @param <E>
     * @return
     */
    public static <E extends Enum<?> & BaseCodeValueEnum> E getCodeValueEnumByCode(Class<E> enumClass, String code) {
        E[] enumConstants = enumClass.getEnumConstants();
        for (E e : enumConstants) {
            if (e.getCode().equals(code)) {
                return e;
            }
        }
        return null;
    }

    /**
     * 根据value返回BaseCodeValueEnum类型的Enum
     * @param enumClass
     * @param value
     * @param <E>
     * @return
     */
    public static <E extends Enum<?> & BaseCodeValueEnum> E getCodeValueEnumByValue(Class<E> enumClass, String value) {
        E[] enumConstants = enumClass.getEnumConstants();
        for (E e : enumConstants) {
            if (e.getValue().equals(value)) {
                return e;
            }
        }
        return null;
    }

    /**
     * 根据key返回BaseKeyValueEnum类型的Enum
     * @param enumClass
     * @param key
     * @param <E>
     * @return
     */
    public static <E extends Enum<?> & BaseKeyValueEnum> E getKeyValueEnumByKey(Class<E> enumClass, Integer key) {
        E[] enumConstants = enumClass.getEnumConstants();
        for (E e : enumConstants) {
            if (e.getKey().equals(key)) {
                return e;
            }
        }
        return null;
    }

    /**
     * 根据value返回BaseKeyValueEnum类型的Enum
     * @param enumClass
     * @param value
     * @param <E>
     * @return
     */
    public static <E extends Enum<?> & BaseKeyValueEnum> E getKeyValueEnumByValue(Class<E> enumClass, String value) {
        E[] enumConstants = enumClass.getEnumConstants();
        for (E e : enumConstants) {
            if (e.getValue().equals(value)) {
                return e;
            }
        }
        return null;
    }
}

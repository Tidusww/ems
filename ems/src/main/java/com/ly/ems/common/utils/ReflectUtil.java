package com.ly.ems.common.utils;

import com.ly.ems.model.common.constant.EnableEnum;
import com.ly.ems.model.config.SystemConfig;
import com.ly.ems.model.config.constant.SystemConfigTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

/**
 * Created by tidus on 2018/10/3.
 */
public class ReflectUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReflectUtil.class);

    /**
     * 根据属性名获取对象中的属性值
     *
     * @param fieldName 属性名
     * @param target    对象
     * @return
     * @throws NoSuchFieldException
     */
    public static Object getFieldValueByFieldName(String fieldName, Object target) throws NoSuchFieldException {
        Object object = null;
        Field field = getFieldByClass(fieldName, target.getClass());
        field.setAccessible(true);
        try {
            object = field.get(target);
        } catch (IllegalAccessException e) {
            LOGGER.error(String.format("获取%s对象中的%s属性失败", target.toString(), fieldName), e);
        }
        return object;
    }

    /**
     * 根据属性名设置对象中的属性值
     *
     * @param fieldName 属性名
     * @param fieldValue 属性值
     * @param target    对象
     * @return
     * @throws NoSuchFieldException
     */
    public static void setFieldValueByFieldName(String fieldName, Object fieldValue, Object target) throws NoSuchFieldException {
        Field field = getFieldByClass(fieldName, target.getClass());
        field.setAccessible(true);
        try {
            field.set(target, fieldValue);
        } catch (IllegalAccessException e) {
            LOGGER.error(String.format("获取%s对象中的%s属性失败", target.toString(), fieldName), e);
        }
    }

    /**
     * 获取对象包括父类中的属性
     *
     * @param fieldName   属性名
     * @param originClass 类
     * @return
     * @throws NoSuchFieldException
     */
    public static Field getFieldByClass(String fieldName, Class originClass) throws NoSuchFieldException {
        Field field = null;
        Class clazz = originClass;

        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                field = clazz.getDeclaredField(fieldName);
                if (field != null) {
                    break;
                }
            } catch (NoSuchFieldException e) {
                LOGGER.warn("{}类中不存在{}属性", clazz.toString(), fieldName);
            }
        }

        if (field == null) {
            throw new NoSuchFieldException(String.format("%s类及其所有父类中都不存在%s属性", originClass, fieldName));
        }

        return field;
    }


    public static void main(String[] args) throws Exception {
        SystemConfig sc = new SystemConfig();
        sc.setEnable(EnableEnum.ENABLED);
        sc.setId(1);
        sc.setConfigType(SystemConfigTypeEnum.BASIC_SALARY);
        sc.setConfigValue("100");
        sc.setConfigDesc("基本工资");

        System.out.println(getFieldValueByFieldName("configDesc", sc));
    }
}

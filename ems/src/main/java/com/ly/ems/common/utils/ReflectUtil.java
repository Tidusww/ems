package com.ly.ems.common.utils;

import com.ly.ems.model.common.constant.EnableEnum;
import com.ly.ems.model.config.SystemConfig;
import com.ly.ems.model.config.constant.SystemConfigTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tidus on 2018/10/3.
 */
public class ReflectUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReflectUtil.class);

    /**
     * 1.1、根据属性名获取对象中的属性值
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
     * 1.2、根据属性名获取对象中的属性类型
     *
     * @param fieldName 属性名
     * @param target    对象
     * @return
     * @throws NoSuchFieldException
     */
    public static Class<?> getFieldTypeByFieldName(String fieldName, Object target) throws NoSuchFieldException {
        Class<?> clazz;
        Field field = getFieldByClass(fieldName, target.getClass());
        clazz = field.getType();
        return clazz;
    }

    /**
     * 2、根据属性名设置对象中的属性值
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
            LOGGER.error(String.format("设置%s对象中的%s属性失败", target.toString(), fieldName), e);
        }
    }

    /**
     * 3、获取对象包括父类中的属性
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


    /**
     * 4、返回类中指定属性的getter方法
     * @param fieldNames
     * @param clazz
     * @return
     */
    public static List<Method> getGetterMethodByFieldNames(List<String> fieldNames, Class clazz) {
        Method[] allMethods = clazz.getMethods();
        List<Method> methods = new ArrayList<Method>();

        for (String fieldName : fieldNames) {
            for (Method method : allMethods) {
                if (method.getName().equalsIgnoreCase("get" + fieldName)) {
                    methods.add(method);
                    break;
                }
            }
        }
        return methods;
    }

    /**
     * 5、返回类中被指定注解标记的字段
     * @param annotation
     * @param clazz
     * @return
     */
    public static List<Field> getFieldByAnnotation(Class annotation, Class clazz) {
        // 得到所有定义字段
        Field[] allFields = clazz.getDeclaredFields();
        List<Field> fields = new ArrayList<Field>();
        for (Field field : allFields) {
            if (field.isAnnotationPresent(annotation)) {
                fields.add(field);
            }
        }
        return fields;
    }


    public static void main(String[] args) throws Exception {
        SystemConfig sc = new SystemConfig();
        sc.setEnable(EnableEnum.ENABLED);
        sc.setId(1);
        sc.setConfigType(SystemConfigTypeEnum.HOUSE_FUND_ALLOWANCE);
        sc.setConfigValue("100");
        sc.setConfigDesc("住房补贴");

        System.out.println(getFieldValueByFieldName("configDesc", sc));
    }
}

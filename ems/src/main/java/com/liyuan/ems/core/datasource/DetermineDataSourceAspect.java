package com.liyuan.ems.core.datasource;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;


/**
 * Created by tidus on 2017/9/4.
 */
public class DetermineDataSourceAspect {

    private String originDataSource;

    public void before(JoinPoint joinPoint) {
        this.originDataSource = "";
        String dataSource = this.dataSourceAnnotationValue(joinPoint);
        if (!StringUtils.isEmpty(dataSource)) {
            this.originDataSource = MultipleRoutingDataSource.getCurrentDataSourceKey();
            MultipleRoutingDataSource.setDataSourceKey(dataSource);
        }
    }

    public void after(JoinPoint joinPoint) {
        //还原为原来的数据源
        if (!StringUtils.isEmpty(this.originDataSource)) {
            MultipleRoutingDataSource.setDataSourceKey(this.originDataSource);
            this.originDataSource = "";
        }
    }

    /**
     * @param joinPoint
     * @return
     */
    private String dataSourceAnnotationValue(JoinPoint joinPoint) {

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();

        String dds;
        //优先方法上的数据源注解
        DetermineDataSource methodDDSAnnotation = method.getAnnotation(DetermineDataSource.class);
        if (methodDDSAnnotation != null) {
            dds = methodDDSAnnotation.value();
            if (dds != null && !"".equals(dds)) {
                return dds;
            }
        }

        //再找Service上的注解
        Class clazz = methodSignature.getDeclaringType();
        DetermineDataSource clazzDDSAnnotation = (DetermineDataSource) clazz.getAnnotation(DetermineDataSource.class);
        if (clazzDDSAnnotation != null) {
            dds = clazzDDSAnnotation.value();
            if (dds != null && !"".equals(dds)) {
                return dds;
            }
        }

        return null;
    }
}

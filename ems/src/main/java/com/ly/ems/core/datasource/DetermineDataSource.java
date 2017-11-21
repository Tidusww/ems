package com.ly.ems.core.datasource;
import java.lang.annotation.*;

/**
 * Created by tidus on 2017/9/4.
 *
 * 标记于Service或者Service中的方法
 * 由 DetermineDataSourceAspect 判断该Service或者方法需要用哪个数据源
 * 默认ADMIN
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface DetermineDataSource {
    String value() default MultipleRoutingDataSource.DATA_SOURCE_EMS;
}

package com.ly.ems.core.springmvc.databinder;

import java.lang.annotation.*;

/**
 * 重写参数名 主要解决管理后台的搜索组件复用传参不一致的问题
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ParamName {

    /**
     * The content of the request parameter to bind to.
     */
    String value();

}
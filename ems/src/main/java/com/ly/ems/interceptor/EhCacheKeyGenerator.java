package com.ly.ems.interceptor;

import org.springframework.cache.interceptor.KeyGenerator;

import java.lang.reflect.Method;

public class EhCacheKeyGenerator implements KeyGenerator {

    @Override
    public Object generate(Object target, Method method, Object... params) {
        StringBuilder sb = new StringBuilder("condition");
        sb.append("_" + target.getClass().getName());
        sb.append("_" + method.getName());
        for (Object obj : params) {
            sb.append("_" + obj);
        }
        return sb.toString();
    }
}

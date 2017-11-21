package com.ly.ems.core.springmvc.controller;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by tidus on 2017/11/18.
 */
public abstract class AbstractBaseController {

    //当前登录用户
    public final static String LOGIN_USER = "LOGIN_USER";


    /**
     * 获取当前项目路径
     * @param request
     * @return
     */
    public String getContext(HttpServletRequest request){
        String context = "http://"+  request.getServerName() + ":"+request.getServerPort() + request.getContextPath();
        return context;
    }

    /**
     * 设置session变量
     * @param request
     * @param key
     * @param val
     */
    public void setSessionAttribute(HttpServletRequest request, String key, Object val) {
        request.getSession().setAttribute(key, val);
    }


    /**
     * 当前登陆用户
     * @param request
     * @param user
     */
    public void setLoginUser(HttpServletRequest request, Object user) {
        request.getSession().setAttribute(LOGIN_USER, user);
    }
    public Object getLoginUser(HttpServletRequest request) {
        return request.getSession().getAttribute(LOGIN_USER);
    }
    public void clearLoginUser(HttpServletRequest request) {
        request.getSession().removeAttribute(LOGIN_USER);
    }
}

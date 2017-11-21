package com.ly.ems.core.shiro.filter;

import com.ly.ems.common.utils.AjaxResult;
import com.ly.ems.core.shiro.ShiroFilterUtil;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by tidus on 2017/11/9.
 */
public class SessionExpiredFilter extends AccessControlFilter {

    @Override
    protected boolean isAccessAllowed(ServletRequest request,
                                      ServletResponse response, Object mappedValue) throws Exception {

        String requestUri = ((HttpServletRequest) request).getRequestURI();
        String contextPath = ((HttpServletRequest) request).getContextPath();
        String url = requestUri.substring(contextPath.length());

        Subject subject = getSubject(request, response);

        if (!subject.isAuthenticated() && ShiroFilterUtil.isAjaxRequest(request)) {
            // session超时 且 Ajax请求
            return false;
        } else {
            return true;
        }

    }

    @Override
    protected boolean onAccessDenied(ServletRequest request,
                                     ServletResponse response) throws Exception {
        String requestUri = ((HttpServletRequest) request).getRequestURI();
        String contextPath = ((HttpServletRequest) request).getContextPath();
        String url = requestUri.substring(contextPath.length());

        //返回Ajax响应
        ShiroFilterUtil.responseAjaxResult(response, AjaxResult.sessionExpired());
        saveRequestAndRedirectToLogin(request, response);


//        Subject subject = getSubject(request, response);
//        if (subject.getPrincipal() == null) {//表示没有登录，重定向到登录页面
//            saveRequest(request);
//            WebUtils.issueRedirect(request, response, LOGIN_URL);
//        } else {
//            if (StringUtils.hasText(UNAUTHORIZED_URL)) {//如果有未授权页面跳转过去
//                WebUtils.issueRedirect(request, response, UNAUTHORIZED_URL);
//            } else {//否则返回401未授权状态码
//                WebUtils.toHttp(response).sendError(HttpServletResponse.SC_UNAUTHORIZED);
//            }
//        }
        return false;
    }

}


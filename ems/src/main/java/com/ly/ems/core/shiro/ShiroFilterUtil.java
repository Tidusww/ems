package com.ly.ems.core.shiro;

import com.ly.ems.common.utils.AjaxResult;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;

/**
 * Created by tidus on 2017/11/9.
 */
public class ShiroFilterUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShiroFilterUtil.class);

    /**
     * 是否是Ajax请求
     *
     * @param request
     * @return
     */
    public static boolean isAjaxRequest(ServletRequest request) {
        String header = ((HttpServletRequest) request).getHeader("X-Requested-With");

        if ("XMLHttpRequest".equalsIgnoreCase(header)) {
            LOGGER.debug("当前请求为Ajax请求");
            return true;
        }

        LOGGER.debug("当前请求非Ajax请求");
        return false;
    }

    public static void responseAjaxResult(ServletResponse response, AjaxResult ajaxResult) {
        ObjectMapper mapper = new ObjectMapper();
        PrintWriter out = null;
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            out = response.getWriter();
            out.println(mapper.writeValueAsString(ajaxResult));
        } catch (Exception e) {
            LOGGER.error("输出JSON报错。" + e.toString());
        } finally {
            if (null != out) {
                out.flush();
                out.close();
            }
        }
    }

}

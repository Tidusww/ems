package com.ly.ems.interceptor;

import com.ly.ems.common.utils.AjaxResult;
import com.ly.ems.core.exception.EMSBusinessException;
import com.ly.ems.core.exception.EMSRuntimeException;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 统一异常处理类
 *
 * @author: zhongtiancai
 * @since V1.0
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public AjaxResult processException(Exception exception) {
        LOGGER.error("系统异常", exception);
        return AjaxResult.fail(StringUtils.isEmpty(exception.getMessage()) ? "系统异常" : exception.getMessage());
    }

    @ExceptionHandler(EMSRuntimeException.class)
    @ResponseBody
    public AjaxResult processRuntimeException(EMSRuntimeException runtimeException) {
//        LOGGER.error("运行异常", runtimeException);
        return AjaxResult.fail(runtimeException.getMessage());
    }

    @ExceptionHandler(EMSBusinessException.class)
    @ResponseBody
    public AjaxResult processBusinessException(EMSBusinessException businessException) {
//        LOGGER.error("业务异常", businessException);
        return AjaxResult.fail(businessException.getMessage());
    }

    /**
     * shiro 内部抛出的异常无法捕捉
     */
    @ExceptionHandler(UnauthorizedException.class)
    @ResponseBody
    public AjaxResult processUnauthorizedException(UnauthorizedException ajaxShiroUnauthorizedException, HttpServletRequest request, HttpServletResponse response) {
        LOGGER.error("授权异常", ajaxShiroUnauthorizedException);
        return AjaxResult.fail("您未授权使用此功能，请联系管理员");
    }
}

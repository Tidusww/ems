package com.ly.ems.core.exception;

/**
 * Created by tidus on 2017/10/31.
 */

/**
 * 业务异常
 */
public class EMSBusinessException extends Exception {

    public EMSBusinessException() {
    }

    public EMSBusinessException(String message) {
        super(message);
    }

    public EMSBusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public EMSBusinessException(Throwable cause) {
        super(cause);
    }

}

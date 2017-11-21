package com.ly.ems.core.exception;

/**
 * Created by tidus on 2017/10/31.
 */
public class EMSBaseException extends RuntimeException {

    public EMSBaseException() {
    }

    public EMSBaseException(String message) {
        super(message);
    }

    public EMSBaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public EMSBaseException(Throwable cause) {
        super(cause);
    }

}

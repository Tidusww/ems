package com.ly.ems.core.exception;

/**
 * Created by tidus on 2017/10/31.
 */
public class EMSRuntimeException extends RuntimeException {

    public EMSRuntimeException() {
    }

    public EMSRuntimeException(String message) {
        super(message);
    }

    public EMSRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public EMSRuntimeException(Throwable cause) {
        super(cause);
    }

}

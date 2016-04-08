package com.xteam.ggq.model.exception;

public class BizException extends RuntimeException {

    public BizException() {
    }

    public BizException(String errorMsg) {
        super(errorMsg);
    }

    public BizException(Throwable cause) {
        super(cause);
    }

    public BizException(String errorMsg, Throwable cause) {
        super(errorMsg, cause);
    }

}

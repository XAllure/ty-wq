package com.ty.wq.exception;

import org.apache.shiro.authc.AuthenticationException;

/**
 * @author Administrator
 * @version 1.0
 * @program: im
 * @description:
 * @date 2020/8/29 19:26
 */
public class VerifyCodeException extends AuthenticationException {

    private static final long serialVersionUID = -1815573027064064685L;

    public VerifyCodeException(){super();}

    public VerifyCodeException(String message) {
        super(message);
    }

    public VerifyCodeException(Throwable cause) {
        super(cause);
    }

    public VerifyCodeException(String message, Throwable cause) {
        super(message, cause);
    }
}

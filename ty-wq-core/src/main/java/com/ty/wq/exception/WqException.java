package com.ty.wq.exception;

import com.ty.wq.enums.ResultEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 自定义异常类
 * @author Administrator
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class WqException extends RuntimeException {

    private static final long serialVersionUID = -8427456639538954108L;

    private Integer code;

    private String msg;

    public WqException() {
        this("");
    }

    public WqException(String msg) {
        this(null, msg);
    }

    public WqException(Integer code) {
        this(code, null);
    }

    public WqException(ResultEnum errorCodeEnum) {
        this(errorCodeEnum.getCode(), errorCodeEnum.getMsg());
    }

    public WqException(Integer code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }
}

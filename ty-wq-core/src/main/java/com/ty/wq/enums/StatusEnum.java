package com.ty.wq.enums;

/**
 * @author Administrator
 */
public enum StatusEnum {

    //枚举类型

    LOCKED(0,"锁定"),
    NORMAL(1,"正常"),

    ;

    private Integer code;

    private String msg;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    StatusEnum(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }

}

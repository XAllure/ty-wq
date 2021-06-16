package com.ty.wq.enums;

/**
 * @author Administrator
 */
public enum StatusEnum {

    //枚举类型

    NORMAL(1,"正常"),
    LOCKED(0,"锁定"),

    LOGGED_IN(1, "已登录"),
    LOGGED_OUT(0, "已退出"),

    ONLINE(1, "在线"),
    OFFLINE(0, "离线"),

    CREATE_GROUP(1, "可建群"),
    CREATE_GROUP_NO(0, "不可建群"),


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

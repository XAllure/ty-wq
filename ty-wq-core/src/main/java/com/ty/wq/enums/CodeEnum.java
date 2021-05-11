package com.ty.wq.enums;

/**
 *
 * @author Administrator
 */
public enum CodeEnum {

    // 枚举方法

    SUCCESS(0, "成功"),

    ERROR(1,"错误"),

    ERROR_ACCOUNT(100,"该账号不存在"),

    ERROR_PASSWORD(101,"密码错误"),

    ERROR_PARAMETER(102, "参数错误"),

    LOCKED_ACCOUNT(103, "该账户已被锁定"),

    NOT_WS_SERVER(104, "没有服务器信息"),

    ERROR_TOKEN(105, "登录信息错误"),

    SERVER_NOT_LOGIN(106, "未登录服务器"),

    NO_TOKEN(106, "token不存在"),

    ERROR_SERVER(500, "服务器内部错误"),

    ;

    private final Integer code;
    private final String msg;

    public String getMsg() {
        return this.msg;
    }

    public Integer getCode() {
        return this.code;
    }

    CodeEnum(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }

}
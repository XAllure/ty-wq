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

    NEED_LOGIN(107,"需要重新登录"),

    NO_TOKEN(108, "token不存在"),

    ERROR_CODE(109, "验证码错误"),

    ERROR_STATUS(110, "状态码错误"),

    METHOD_NOT_SUPPORT(111,"不支持此方法"),

    PASSWORD_2_NOT_SAME(112,"两次密码不一致"),

    PASSWORD_SAME(113,"新密码不能与旧密码一致"),

    ERROR_SAME_DATA(114,"原数据或已存在"),

    ERROR_CLIENT(115,"客户端不存在"),


    NO_AUTHENTICATION(401,"认证失败"),

    NO_AUTHORITY(403,"您没有该操作权限"),

    Request_Method_Not_Supported(405, "不支持该请求方式"),

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

package com.ty.wq.enums;

/**
 * @author Administrator
 * @version 1.0
 * @program: ty-wq
 * @description:
 * @date 2020/4/27 16:18
 */
public enum CompareEnum {
    // 相等
    EQUAL("equal"),
    LIKE("like"),
    GE("ge"),
    LE("le"),
    ;

    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    CompareEnum(String msg){
        this.msg = msg;
    }

}

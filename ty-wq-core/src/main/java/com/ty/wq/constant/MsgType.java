package com.ty.wq.constant;

/**
 * netty 消息类型
 * @author Administrator
 */
public interface MsgType {
    String
            LOGIN = "Login",
            ERROR = "Error",
            HEART_BEAT = "HeartBeat",
            RECEIVED = "Received"

    ;
}

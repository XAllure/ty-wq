package com.ty.wq.constant;

/**
 * netty 消息类型
 * @author Administrator
 */
public interface MsgType {

    /** 登录服务器 */
    String LOGIN = "Login";

    /** 轮询回调客户端登录服务器 */
    String SR_LOGIN = "SrLogin";

    /** 微信消息回调 */
    String RECEIVE_MSG = "ReceiveMsg";

    /** 微信消息轮询 */
    String SEND_MSG = "SendMsg";

    /** 错误 */
    String  ERROR = "Error";

    /** 心跳 */
    String  HEART_BEAT = "HeartBeat";

    /** 已收到 */
    String  RECEIVED = "Received";

}

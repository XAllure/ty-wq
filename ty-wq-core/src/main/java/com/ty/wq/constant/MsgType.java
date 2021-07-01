package com.ty.wq.constant;

/**
 * netty 消息类型
 * @author Administrator
 */
public interface MsgType {

    /** 登录服务器 */
    String  LOGIN = "Login";

    /** 错误 */
    String  ERROR = "Error";

    /** 心跳 */
    String  HEART_BEAT = "HeartBeat";

    /** 已收到 */
    String  RECEIVED = "Received";

    /** 创建群聊 */
    String CREATE_ROOM = "CreateRoom";

    /** 个人信息 */
    String MY_INFO = "MyInfo";

    /** 新好友 */
    String NEW_FRIEND = "NewFriend";

    /** 好友添加成功 */
    String NEW_FRIEND_SUCCESS = "NewFriendSuccess";

    /** 私聊 */
    String PRIVATE_CHAT = "PrivateChat";

    /** 群聊 */
    String ROOM_CHAT = "RoomChat";

}

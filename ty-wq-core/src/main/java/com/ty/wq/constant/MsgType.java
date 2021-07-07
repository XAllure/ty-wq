package com.ty.wq.constant;

/**
 * netty 消息类型
 * @author Administrator
 */
public interface MsgType {

    /** 登录服务器 */
    String  LOGIN = "login";

    /** 微信消息回调 */
    String RECEIVE_MSG = "receiveMsg";

    /** 微信消息轮询 */
    String SEND_MSG = "sendMsg";

    /** 微信登录 */
    String WECHAT_LOGIN = "wechatLogin";

    /** 错误 */
    String  ERROR = "error";

    /** 心跳 */
    String  HEART_BEAT = "heartBeat";

    /** 已收到 */
    String  RECEIVED = "received";

    /** 获取微信普通好友列表 */
    String GET_CONTACTS = "getContacts";

    /** 获取单个普通好友信息 */
    String GET_SINGLE_CONTACT = "getSingleContact";

    /** 添加好友 */
    String ADD_FRIEND = "addFriend";

    /** 接收加好友请求 */
    String ACCEPT_FRIEND = "acceptFriend";

    /** 删除好友 */
    String DEL_FRIEND = "delFriend";

    /** 修改好友备注 */
    String UPDATE_REMARK = "updateRemark";

    /** 接收好友转账 */
    String ACCEPT_BANK_TRANSFER = "acceptBankTransfer";












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

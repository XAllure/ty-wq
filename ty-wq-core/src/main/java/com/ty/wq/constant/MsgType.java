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

    /** 获取普通群列表 */
    String GET_CHAT_ROOMS = "getChatRooms";

    /** 获取单个普通群成员信息 */
    String GET_SINGLE_CHAT_ROOM_MEMBERS = "getSingleChatRoomMembers";

    /** 创建群聊 */
    String CREATE_CHAT_ROOM = "createChatRoom";

    /** 发送文本消息 */
    String SEND_TEXT_MESSAGE = "sendTextMessage";

    /** 发送群内@文本消息 */
    String SEND_AT_TEXT_MESSAGE = "sendAtTextMessage";

    /** 发送图片 */
    String SEND_PIC_MESSAGE = "sendPicMessage";

    /** 发送文件 */
    String SEND_FILE_MESSAGE = "sendFileMessage";

    /** 发送视频 */
    String SEND_VIDEO_MESSAGE = "sendVideoMessage";

    /** 发送GIF表情 */
    String SEND_GIF_MESSAGE = "sendGifMessage";

    /** 发送链接消息 */
    String SEND_LINK_MESSAGE = "sendLinkMessage";

    /** 发送名片 */
    String SEND_CARD_MESSAGE = "sendCardMessage";

    /** 发送小程序 */
    String SEND_MINI_MESSAGE = "sendMiniMessage";




    /** 私聊 */
    String PRIVATE_CHAT = "PrivateChat";

    /** 群聊 */
    String ROOM_CHAT = "RoomChat";

}

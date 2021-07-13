package com.ty.wq.constant;

/**
 * netty 消息类型
 * @author Administrator
 */
public interface ApiType {

    /** 获取当前登录微信详细信息 */
    String GET_LOGIN_USER = "getLoginUser";

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

    /** 接收好友转账 */
    String ACCEPT_BANK_TRANSFER = "acceptBankTransfer";

    /** 退还好友转账 */
    String REFUSE_FRIEND_WCPAY = "refuseFriendWcpay";

    /** 置顶/取消置顶聊天 */
    String CHAT_SESSION_TOP = "chatSessionTop";

    /** 开启/关闭消息免打扰 */
    String MOD_RECV_NOTIFY = "modRecvNotify";

    /** 创建群聊 */
    String CREATE_CHAT_ROOM = "createChatRoom";

    /** 退出并删除群 */
    String QUIT_DEL_CHAT_ROOM = "quitDelChatRoom";

    /** 踢群成员 */
    String DEL_CHAT_ROOM_MEMBERS = "delChatRoomMembers";

    /** 修改群名称 */
    String UPDATE_CHAT_ROOM_NAME = "updateChatRoomName";

    /** 修改我在本群的昵称 */
    String UPDATE_CHAT_ROOM_DISPLAY_NAME = "updateChatRoomDisplayName";

    /** 发送40人以下群邀请 不需要好友同意即可直接拉入群 */
    String SEND_CHATROOM_LOW = "sendChatroomLow";

    /** 发送40人以上群邀请 需要好友同意 */
    String SEND_CHATROOM_HIGH = "sendChatroomHigh";

    /** 接受群邀请 */
    String ACCEPT_CHATROOM_INVITE = "acceptChatroomInvite";

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

    /** 发送xml消息 */
    String SEND_XML_MESSAGE = "sendXmlMessage";

    /** 发送名片xml消息 */
    String SEND_CARD_XML_MESSAGE = "sendCardXmlMessage";

}

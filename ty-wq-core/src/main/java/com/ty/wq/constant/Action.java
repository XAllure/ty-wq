package com.ty.wq.constant;

/**
 * WeQuick轮询返回的action类型
 * @author Administrator
 */
public interface Action {

    /** 上报当前登录微信详细信息 */
    String REPORT_LOGIN_USER = "reportLoginUser";

    /** 上报普通好友列表 */
    String REPORT_CONTACTS = "reportContacts";

    /** 上报单个普通好友信息 */
    String REPORT_SINGLE_CONTACT = "reportSingleContact";

    /** 上报任意普通微信反查详细信息 */
    String REPORT_UPDATE_CONTACT = "reportUpdateContact";;

    /** 上报联系人新增通知 */
    String REPORT_CONTACT_ADD = "reportContactAdd";

    /** 上报联系人删除通知 */
    String REPORT_CONTACT_DEL = "reportContactDel";

    /** 上报新的加好友请求 */
    String REPORT_FRIEND_ADD_REQUEST = "reportFriendAddRequest";

    /** 上报加好友指令返回状态 */
    String REPORT_ADD_FRIEND_MESSAGE = "reportAddFriendMessage";

    /** 上报通过手机号/微信号/QQ号查询任意微信号信息 */
    String REPORT_SEARCH_CONTACT = "reportSearchContact";

    /** 上报当前聊天对象改变 */
    String REPORT_TALKER_CHANGE = "reportTalkerChange";

    /** 上报普通群列表 */
    String REPORT_CHAT_ROOMS = "reportChatRooms";

    /** 上报单个普通群成员信息 */
    String REPORT_SINGLE_CHAT_ROOM_MEMBERS = "reportSingleChatRoomMembers";

    /** 上报群成员新增通知 */
    String REPORT_CHAT_ROOM_MEMBER_ADD = "reportChatRoomMemberAdd";

    /** 上报群成员删除通知 */
    String REPORT_CHAT_ROOM_MEMBER_DEL = "reportChatRoomMemberDel";

    /** 上报检测到的新群通知 */
    String REPORT_NEW_CHAT_ROOM = "reportNewChatRoom";

    /** 上报退群或被踢通知 */
    String REPORT_CHAT_ROOM_QUIT = "reportChatRoomQuit";

    /** 上报文本消息 */
    String REPORT_TEXT_MESSAGE = "reportTextMessage";

    /** 上报图片消息 */
    String REPORT_PIC_MESSAGE = "reportPicMessage";

    /** 上报文件消息 */
    String REPORT_FILE_MESSAGE = "reportFileMessage";

    /** 上报视频消息 */
    String REPORT_VIDEO_MESSAGE = "reportVideoMessage";

    /** 上报语音消息 */
    String REPORT_VOICE_MESSAGE = "reportVoiceMessage";

    /** 上报GIF表情消息 */
    String REPORT_GIF_MESSAGE = "reportGifMessage";

    /** 上报个人名片消息 */
    String REPORT_CARD_MESSAGE = "reportCardMessage";

    /** 上报位置消息 */
    String REPORT_LOCATION_MESSAGE = "reportLocationMessage";

    /** 上报链接消息 */
    String REPORT_LINK_MESSAGE = "reportLinkMessage";

    /** 上报小程序消息 */
    String REPORT_MINI_MESSAGE = "reportMiniMessage";

    /** 上报转账消息 */
    String REPORT_TRANSFER_MESSAGE = "reportTransferMessage";

    /** 上报系统消息 */
    String REPORT_SYSTEM_MESSAGE = "reportSystemMessage";

    /** 上报其他消息 */
    String REPORT_OTHER_MESSAGE = "reportOtherMessage";

    /** 上报其他接收应用未知消息 */
    String REPORT_OTHER_APP_MESSAGE = "reportOtherAppMessage";

    /** 上报无痕清理僵尸粉消息 */
    String REPORT_ZOMBIE_CHECK_MESSAGE = "reportZombieCheckMessage";

    /** 上报URL访问状态 */
    String REPORT_CHECK_URL_STATUS = "reportCheckUrlStatus";

    /** 上报上传客户端文件到服务端结果 */
    String RES_UPLOAD_FILE = "resUploadFile";
}

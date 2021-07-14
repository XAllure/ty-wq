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
}

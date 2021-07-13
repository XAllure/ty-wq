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

}

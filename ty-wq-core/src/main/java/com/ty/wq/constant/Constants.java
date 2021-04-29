package com.ty.wq.constant;

import io.netty.util.AttributeKey;

/**
 * @author Administrator
 */
public interface Constants {

    /** 登录信息 */
    String WQ_LOGIN_KEY = "WQ-LOGIN-KEY:";

    /** 短信信息 */
    String WQ_SMS_KEY = "WQ-SMS-KEY:";

    /** netty 服务器信息 */
    String WS_SERVER_INFO = "WQ-WS-SERVER-INFO-";

    /** 用户对应的服务器 */
    String WS_USER_SERVER = "WQ-WS-USER-SERVER-";

    /** 服务器注册超时时间 */
    int REG_TIMEOUT = 80;

    /** 屏蔽词文件名 */
    String BADWORDS_FILE_NAME = "badwords.txt";

    /** 用户连接 netty 进行登录时 绑定的 token 属性 */
    AttributeKey<String> WS_TOKEN = AttributeKey.valueOf("token");

}

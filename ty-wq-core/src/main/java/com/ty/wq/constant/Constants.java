package com.ty.wq.constant;

import io.netty.util.AttributeKey;

/**
 * @author Administrator
 */
public interface Constants {

    /** 用户登录token头部 */
    String WQ_TOKEN_HEADER = "WQ-LOGIN-TOKEN";

    /** 登录信息 */
    String WQ_USER_LOGIN_KEY = "WQ-USER-LOGIN-KEY:";

    /** 短信信息 */
    String WQ_SMS_KEY = "WQ-SMS-KEY:";

    /** netty 服务器信息 */
    String WQ_SERVER_INFO = "WQ-SERVER-INFO-";

    String WQ_ROUTE_INFO = "WQ-ROUTE-INFO-";

    /** 用户对应的服务器 */
    String WQ_USER_SERVER = "WQ-USER-SERVER-";

    /** 服务器注册超时时间 */
    int REG_TIMEOUT = 80;

    /** 屏蔽词文件名 */
    String BAD_WORDS_FILE_NAME = "badwords.txt";

    /**  */
    String CONTENT_TYPE_JSON = "application/json"


    ;

}

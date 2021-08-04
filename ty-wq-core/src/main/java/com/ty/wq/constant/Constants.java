package com.ty.wq.constant;

import io.netty.util.AttributeKey;
import io.swagger.annotations.ApiModelProperty;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author Administrator
 */
@ApiIgnore
public interface Constants {

    @ApiModelProperty("轮询回调客户端登录信息")
    String SR_LOGIN_KEY = "WQ-SR-LOGIN-KEY:";

    @ApiModelProperty("用户登录token头部")
    String WQ_TOKEN_HEADER = "WQ-LOGIN-TOKEN";

    @ApiModelProperty("用户登录信息")
    String WQ_USER_LOGIN_KEY = "WQ-USER-LOGIN-KEY:";

    @ApiModelProperty("token时效(秒)")
    int TOKEN_EXPIRE = 86400;

    @ApiModelProperty("用户权限")
    String USER_PERMISSION_KEY = "WQ-USER-PERMISSION-KEY:";

    @ApiModelProperty("短信信息")
    String WQ_SMS_KEY = "WQ-SMS-KEY:";

    @ApiModelProperty("netty服务器信息")
    String WQ_SERVER_INFO = "WQ-SERVER-INFO-";

    @ApiModelProperty("路由信息")
    String WQ_ROUTE_INFO = "WQ-ROUTE-INFO-";

    @ApiModelProperty("用户对应的服务器")
    String WQ_USER_SERVER = "WQ-USER-SERVER-";

    @ApiModelProperty("服务器注册超时时间")
    int REG_TIMEOUT = 80;

    @ApiModelProperty("屏蔽词文件名")
    String BAD_WORDS_FILE_NAME = "badwords.txt";

    @ApiModelProperty("json类型")
    String CONTENT_TYPE_JSON = "application/json"

    ;

}

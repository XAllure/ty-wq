package com.ty.wq.pojo.vo.client.user;

import com.ty.wq.pojo.vo.netty.WsServer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Administrator
 */
@Data
@ApiModel
public class LoginRespVo {

    private static final long serialVersionUID = -8131312128423682551L;

    @ApiModelProperty("登录的token")
    private String token;

    @ApiModelProperty("用户信息")
    private UserRespVo user;

    @ApiModelProperty("netty服务器信息")
    private WsServer server;

}

package com.ty.wq.pojo.vo.client.user;

import com.ty.wq.pojo.vo.netty.WsServer;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Administrator
 */
@Data
public class LoginRespVo {

    private static final long serialVersionUID = -8131312128423682551L;

    private String token;

    private UserRespVo user;

    private WsServer server;

}

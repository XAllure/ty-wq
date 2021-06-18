package com.ty.wq.config.netty;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Administrator
 */
@Component
@Data
public class NettyInfoConfig {

    /** netty服务器编号 */
    @Value("${custom.netty.websocket.id}")
    private String id;

    /** 公网ip */
    @Value("${custom.netty.websocket.ip}")
    private String pIp;

    /** netty监听端口 */
    @Value("${custom.netty.websocket.port}")
    private Integer nPort;

    /** http端口 */
    @Value("${custom.netty.websocket.hport}")
    private Integer hPort;

}

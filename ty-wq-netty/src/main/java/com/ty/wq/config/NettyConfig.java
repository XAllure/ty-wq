package com.ty.wq.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Administrator
 */
@Component
@Data
public class NettyConfig {

    /** netty服务器编号 */
    @Value("${custom.netty.websocket.id}")
    private String id;

    /** 公网ip */
    @Value("${custom.netty.websocket.ip}")
    private String pip;

    /** netty监听端口 */
    @Value("${custom.netty.websocket.port}")
    private Integer nport;

    /** http端口 */
    @Value("${server.port}")
    private Integer hport;

}

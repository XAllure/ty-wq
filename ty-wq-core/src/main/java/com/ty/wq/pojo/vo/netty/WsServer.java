package com.ty.wq.pojo.vo.netty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * WebSocketServerInfo
 * @author Administrator
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WsServer implements Serializable {

    private static final long serialVersionUID = -4973933492129552518L;

    /** netty服务器编号 */
    private String id;

    /** 公网ip */
    private String pip;

    /** 内网ip */
    private String nip;

    /** netty监听端口 */
    private Integer nport;

    /** http端口 */
    private Integer hport;

}

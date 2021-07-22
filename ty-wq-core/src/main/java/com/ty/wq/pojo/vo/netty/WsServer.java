package com.ty.wq.pojo.vo.netty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel
public class WsServer implements Serializable {

    private static final long serialVersionUID = -4973933492129552518L;

    @ApiModelProperty("netty服务器编号")
    private String id;

    @ApiModelProperty("公网ip")
    private String pip;

    @ApiModelProperty("内网ip")
    private String nip;

    @ApiModelProperty("netty监听端口")
    private Integer nport;

    @ApiModelProperty("http端口")
    private Integer hport;

}

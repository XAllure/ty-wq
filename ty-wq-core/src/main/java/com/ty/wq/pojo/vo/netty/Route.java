package com.ty.wq.pojo.vo.netty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Administrator
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class Route implements Serializable {

    private static final long serialVersionUID = 9015019551322787996L;

    @ApiModelProperty("路由id")
    private String id;

    @ApiModelProperty("路由ip地址")
    private String ip;

    @ApiModelProperty("路由端口")
    private Integer port;

}

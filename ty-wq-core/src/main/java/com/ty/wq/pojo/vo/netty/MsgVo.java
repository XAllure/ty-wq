package com.ty.wq.pojo.vo.netty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Administrator
 */
@Data
@ApiModel
public class MsgVo {

    @ApiModelProperty("消息类型")
    private String type;

    @ApiModelProperty("消息token")
    private String token;

    @ApiModelProperty("数据")
    private Object data;

}

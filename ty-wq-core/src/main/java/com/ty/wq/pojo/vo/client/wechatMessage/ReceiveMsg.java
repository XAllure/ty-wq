package com.ty.wq.pojo.vo.client.wechatMessage;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Administrator
 */
@Data
@ApiModel
public class ReceiveMsg {

    @ApiModelProperty("上报的名称")
    private String action;

    @ApiModelProperty("当前登录微信账号的微信ID")
    private String cwxid;

    @ApiModelProperty("上报的数据")
    private Object data;
}

package com.ty.wq.pojo.vo.netty.report;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Administrator
 */
@Data
@ApiModel("新的加好友请求")
public class WechatFriendAddVo {

    @ApiModelProperty("消息类型标记")
    private Integer msgType;

    @ApiModelProperty("是否由PC端发出，1为是，0为不是")
    private Integer ispc;

    @ApiModelProperty("消息ID，可用于转发")
    private String msgid;

    @ApiModelProperty("聊天消息发生在哪个群(如果是私聊则为空)")
    private String roomWxid;

    @ApiModelProperty("消息发送者的wxid")
    private String wxidFrom;

    @ApiModelProperty("消息的接收者的wxid")
    private String wxidTo;

    @ApiModelProperty("加好友请求xml数据")
    private String xmlmsg;

    @ApiModelProperty("消息时间戳")
    private Long timestamp;

}

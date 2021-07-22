package com.ty.wq.pojo.vo.netty.report;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Administrator
 */
@Data
@ApiModel
public class WechatMessageVo {

    @ApiModelProperty("消息类型标记")
    private Integer msgType;

    @ApiModelProperty("是否是本人发出的消息，1为是，0为不是")
    private Integer myMsg;

    @ApiModelProperty("是否由PC端发出，1为是，0为不是")
    private Integer ispc;

    @ApiModelProperty("消息ID，可用于转发")
    private String msgid;

    @ApiModelProperty("聊天消息发生在哪个群(如果是私聊则为空)")
    private String roomWxid;

    @ApiModelProperty("消息发送者的wxid 如果是自己发的消息这里的wxid就是自己的微信号")
    private String wxidFrom;

    @ApiModelProperty("消息的接收者的wxid 如果发往群的消息,这个值就是群的wxid 如果是别人私聊给自己的消息,这里就是自己的微信号")
    private String wxidTo;

    @ApiModelProperty("消息内容，纯文本格式")
    private String message;

    @ApiModelProperty("群内发送消息@用户列表")
    private String atlist;

    @ApiModelProperty("图片本地路径")
    private String image;

    @ApiModelProperty("缩略图本地路径")
    private String imagethumb;

    @ApiModelProperty("文件下载后的本地路径")
    private String fileIndex;

    @ApiModelProperty("视频文件封面图片的本地路径")
    private String coverIndex;

    @ApiModelProperty("视频文件下载后的本地路径")
    private String videoIndex;

    @ApiModelProperty("语音文件下载后的本地路径")
    private String voiceIndex;

    @ApiModelProperty("语音转换后的本地路径")
    private String mp3Index;

    @ApiModelProperty("微信原始的xml信息")
    private String xmlmsg;

    @ApiModelProperty("消息时间戳")
    private Long timestamp;

}

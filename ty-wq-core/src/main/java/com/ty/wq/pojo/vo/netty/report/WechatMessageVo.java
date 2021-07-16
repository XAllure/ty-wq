package com.ty.wq.pojo.vo.netty.report;

import lombok.Data;

/**
 * @author Administrator
 */
@Data
public class WechatMessageVo {

    /** 消息类型标记 */
    private Integer msgType;

    /** 是否是本人发出的消息，1为是，0为不是 */
    private Integer myMsg;

    /** 是否由PC端发出，1为是，0为不是 */
    private Integer ispc;

    /** 消息ID，可用于转发 */
    private String msgid;

    /** 聊天消息发生在哪个群(如果是私聊则为空) */
    private String roomWxid;

    /** 消息发送者的wxid 如果是自己发的消息这里的wxid就是自己的微信号 */
    private String wxidFrom;

    /** 消息的接收者的wxid 如果发往群的消息,这个值就是群的wxid 如果是别人私聊给自己的消息,这里就是自己的微信号 */
    private String wxidTo;

    /** 消息内容，纯文本格式 */
    private String message;

    /** 群内发送消息@用户列表 */
    private String atlist;

    /** 图片本地路径 */
    private String image;

    /** 缩略图本地路径 */
    private String imagethumb;

    /** 文件下载后的本地路径 */
    private String fileIndex;

    /** 视频文件封面图片的本地路径 */
    private String coverIndex;

    /** 视频文件下载后的本地路径 */
    private String videoIndex;

    /** 语音文件下载后的本地路径 */
    private String voiceIndex;

    /** 语音转换后的本地路径 */
    private String mp3Index;

    /** 消息时间戳 */
    private Long timestamp;

    /** 微信原始的xml信息 */
    private String xmlmsg;

}

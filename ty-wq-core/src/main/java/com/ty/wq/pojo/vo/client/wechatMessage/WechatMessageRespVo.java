package com.ty.wq.pojo.vo.client.wechatMessage;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.ty.wq.pojo.vo.BaseRespVo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.sql.Timestamp;

/**
 * @author Administrator
 * @version 1.0
 * @program: ty-wq
 * @description:
 * @date 2021-06-21 03:41:10
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class WechatMessageRespVo extends BaseRespVo {

    private static final long serialVersionUID = -1L;

    /** 微信id */
    private String wechatId;

    /** 消息类型 1-文本 3-图片 4906-文件 43-视频 34-语音 47-gif表情 42-个人名片 48-位置消息 4905-链接消息 4933-小程序消息 4920-转账消息 */
    private Integer msgType;

    /** 是否发送 1-发送 0-接受 */
    private Integer isSend;

    /** 是否pc端发出 1-是 0-否 */
    private Integer isPc;

    /** 消息ID */
    private String msgId;

    /** 服务端消息ID */
    private String msgSvrId;

    /** 群ID 聊天消息发生在哪个群(如果是私聊则为空) */
    private String roomWxId;

    /** 微信发送者 */
    private String wxIdFrom;

    /** 微信接收者 如果发往群的消息,这个值就是群的wxid 如果是别人私聊给自己的消息,这里就是自己的微信号 */
    private String wxIdTo;

    /** 消息内容 存json数据 */
    private String content;

    /** 公司ID */
    private Long companyId;

    /** 部门ID */
    private Long departmentId;

    /** 消息时间戳 */
    private Long timestamp;

}

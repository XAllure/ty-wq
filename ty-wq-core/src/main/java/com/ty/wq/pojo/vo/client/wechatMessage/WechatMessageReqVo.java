package com.ty.wq.pojo.vo.client.wechatMessage;

import com.ty.wq.pojo.vo.BaseReqVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Administrator
 * @version 1.0
 * @program: ty-wq
 * @description:
 * @date 2021-06-21 03:41:10
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel
public class WechatMessageReqVo extends BaseReqVo {

    private static final long serialVersionUID = -1L;

    @ApiModelProperty("微信id")
    @NotBlank(message = "微信id不能为空", groups = {Chat.class, At.class, Get.class})
    private String wechatId;

    @ApiModelProperty("消息类型 1-文本 3-图片 4906-文件 43-视频 34-语音 47-gif表情 42-个人名片 48-位置消息 4905-链接消息 4933-小程序消息 4920-转账消息")
    private Integer msgType;

    @ApiModelProperty("是否发送 1-发送 0-接受")
    private Integer isSend;

    @ApiModelProperty("是否pc端发出 1-是 0-否")
    private Integer isPc;

    @ApiModelProperty("群ID 聊天消息发生在哪个群(如果是私聊则为空)")
    @NotBlank(message = "群ID不能为空", groups = {At.class})
    private String roomWxId;

    @ApiModelProperty("微信发送者")
    @NotBlank(message = "微信发送者不能为空", groups = {Chat.class, At.class, Get.class})
    private String wxIdFrom;

    @ApiModelProperty("微信接收者 如果发往群的消息,这个值就是群的wxid 如果是别人私聊给自己的消息,这里就是自己的微信号")
    @NotBlank(message = "微信接收者不能为空", groups = {Chat.class, At.class, Get.class})
    private String wxIdTo;

    @ApiModelProperty("消息内容 存json数据")
    @NotBlank(message = "消息内容不能为空", groups = {Chat.class, At.class})
    private String content;

    @ApiModelProperty("公司ID")
    private Long companyId;

    @ApiModelProperty("部门ID")
    private Long departmentId;

    @ApiModelProperty("要@的成员微信ID")
    @NotEmpty(message = "请选择要@的成员", groups = {At.class})
    private List<String> atList;

}

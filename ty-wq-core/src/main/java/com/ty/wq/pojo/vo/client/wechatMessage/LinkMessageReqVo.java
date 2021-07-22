package com.ty.wq.pojo.vo.client.wechatMessage;

import com.ty.wq.pojo.vo.BaseReqVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author Administrator
 */
@Data
@ApiModel
public class LinkMessageReqVo {

    @ApiModelProperty("微信id")
    @NotBlank(message = "微信id不能为空", groups = {BaseReqVo.Chat.class})
    private String wechatId;

    @ApiModelProperty("要发送的好友微信id")
    @NotBlank(message = "要发送的好友微信id不能为空", groups = {BaseReqVo.Chat.class})
    private String wxIdTo;

    @ApiModelProperty("标题")
    @NotBlank(message = "标题不能为空", groups = {BaseReqVo.Chat.class})
    private String title;

    @ApiModelProperty("url链接")
    @NotBlank(message = "url链接不能为空", groups = {BaseReqVo.Chat.class})
    private String url;

    @ApiModelProperty("描述")
    private String desc;

    @ApiModelProperty("图片url链接")
    private String pic;

}

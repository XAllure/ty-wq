package com.ty.wq.pojo.vo.client.wechatMessage;

import com.ty.wq.pojo.vo.BaseReqVo;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author Administrator
 */
@Data
public class LinkMessageReqVo {

    /** 微信id */
    @NotBlank(message = "微信id不能为空", groups = {BaseReqVo.Chat.class})
    private String wechatId;

    /** 标题 */
    @NotBlank(message = "要发送的好友微信id不能为空", groups = {BaseReqVo.Chat.class})
    private String wxIdTo;

    /** url链接 */
    @NotBlank(message = "标题不能为空", groups = {BaseReqVo.Chat.class})
    private String title;

    /** url链接 */
    @NotBlank(message = "url链接不能为空", groups = {BaseReqVo.Chat.class})
    private String url;

    /** 描述 */
    private String desc;

    /** 图片url链接 */
    private String pic;

}

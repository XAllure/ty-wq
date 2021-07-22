package com.ty.wq.pojo.vo.client.wechatRoom;

import com.ty.wq.pojo.vo.BaseReqVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author Administrator
 */
@Data
@ApiModel("接受群聊")
public class AcceptChatroomReqVo implements Serializable {

    private static final long serialVersionUID = 3274264753971261872L;

    @ApiModelProperty("微信ID")
    @NotBlank(message = "微信ID不能为空", groups = {BaseReqVo.Invite.class})
    private String wechatId;

    @ApiModelProperty("好友微信ID")
    @NotBlank(message = "好友微信ID不能为空", groups = {BaseReqVo.Invite.class})
    private String friendId;

    @ApiModelProperty("入群链接地址")
    @NotBlank(message = "入群链接地址不能为空", groups = {BaseReqVo.Invite.class})
    private String inviteUrl;


}

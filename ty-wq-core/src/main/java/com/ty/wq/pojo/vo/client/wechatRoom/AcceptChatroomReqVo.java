package com.ty.wq.pojo.vo.client.wechatRoom;

import com.ty.wq.pojo.vo.BaseReqVo;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author Administrator
 */
@Data
public class AcceptChatroomReqVo implements Serializable {

    private static final long serialVersionUID = 3274264753971261872L;

    @NotBlank(message = "微信ID不能为空", groups = {BaseReqVo.Invite.class})
    private String wechatId;

    @NotBlank(message = "好友微信ID不能为空", groups = {BaseReqVo.Invite.class})
    private String friendId;

    @NotBlank(message = "入群链接地址不能为空", groups = {BaseReqVo.Invite.class})
    private String inviteUrl;


}

package com.ty.wq.controller;

import com.ty.wq.constant.MsgType;
import com.ty.wq.pojo.vo.Result;
import com.ty.wq.pojo.vo.client.wechatMessage.SendMsg;
import com.ty.wq.pojo.vo.netty.MsgVo;
import com.ty.wq.utils.ChannelUtils;
import com.ty.wq.utils.MsgUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("/wechat/friend")
public class NeWechatFriendController {

    /**
     * 添加好友
     * @param sendMsg
     * @return
     */
    @PostMapping("/addFriend")
    public Result addFriend(SendMsg sendMsg) {
        MsgVo msgVo = new MsgVo();
        msgVo.setType(MsgType.SEND_MSG);
        msgVo.setData(sendMsg);
        MsgUtils.writeJson(ChannelUtils.clientChannel(), msgVo);
        return Result.success();
    }

}

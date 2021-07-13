package com.ty.wq.controller;

import com.ty.wq.constant.MsgType;
import com.ty.wq.pojo.vo.BaseReqVo;
import com.ty.wq.pojo.vo.Result;
import com.ty.wq.pojo.vo.client.wechatMessage.SendMsg;
import com.ty.wq.pojo.vo.netty.Message;
import com.ty.wq.pojo.vo.netty.MsgVo;
import com.ty.wq.utils.ChannelUtils;
import com.ty.wq.utils.MsgUtils;
import com.ty.wq.utils.ReqVoUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("/client")
public class ClientController {

    /**
     * 往转发客户端发送数据
     * @param sendMsg
     * @return
     */
    @PostMapping("/sendMsg")
    public Result addFriend(@RequestBody SendMsg sendMsg) {
        ReqVoUtils.validated(sendMsg, BaseReqVo.Chat.class);
        MsgVo msgVo = new MsgVo();
        msgVo.setType(MsgType.SEND_MSG);
        msgVo.setData(sendMsg);
        MsgUtils.writeJson(ChannelUtils.getClientChannel(sendMsg.getSendId()), Message.success(msgVo));
        return Result.success();
    }

}

package com.ty.wq.handler.websocket;

import com.ty.wq.constant.MsgType;
import com.ty.wq.pojo.vo.client.wechatMessage.SendMsg;
import com.ty.wq.pojo.vo.netty.MsgVo;
import com.ty.wq.service.client.WechatFriendService;
import com.ty.wq.utils.ChannelUtils;
import com.ty.wq.utils.MsgUtils;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 */
@Service
@Slf4j
public class SendMsgHandler {

    /**
     * 获取好友列表
     * @param channel
     * @param msgVo
     */
    @Async
    public void handler(Channel channel, MsgVo msgVo) {
        msgVo.setType(MsgType.SEND_MSG);
        MsgUtils.writeJson(ChannelUtils.clientChannel(), msgVo);
    }

}

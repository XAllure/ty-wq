package com.ty.wq.netty.handler.websocket;

import com.ty.wq.pojo.vo.netty.Message;
import com.ty.wq.pojo.vo.netty.MsgVo;
import com.ty.wq.utils.ChannelUtils;
import com.ty.wq.utils.MessageUtils;
import com.ty.wq.utils.WsTokenUtils;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 登录操作
 * @author Administrator
 */
@Service
@Slf4j
public class LoginHandler {

    @Async
    public void handler(Channel channel, MsgVo msgVo) {
        log.info("用户[{}]执行登录服务器操作！！！", channel.remoteAddress());
        Long userId = WsTokenUtils.getUserId(msgVo.getToken());
        channel.attr(ChannelUtils.WS_TOKEN).set(msgVo.getToken());
        ChannelUtils.save(userId, channel);
        MessageUtils.writeJson(channel, Message.success(msgVo.getType()));
    }

}

package com.ty.wq.handler.websocket;

import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;
import com.ty.wq.constant.Constants;
import com.ty.wq.constant.MsgType;
import com.ty.wq.enums.CodeEnum;
import com.ty.wq.pojo.vo.netty.Message;
import com.ty.wq.pojo.vo.netty.MsgVo;
import com.ty.wq.utils.ChannelUtils;
import com.ty.wq.utils.MsgUtils;
import com.ty.wq.utils.RedisUtils;
import com.ty.wq.utils.WsTokenUtils;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * 登录操作
 * @author Administrator
 */
@Service
@Slf4j
public class LoginHandler {

    @Async
    public void handler(Channel channel, MsgVo msgVo) {
        if (!WsTokenUtils.hasToken(msgVo.getToken())) {
            MsgUtils.writeJson(channel, Message.error(MsgType.ERROR, CodeEnum.NO_TOKEN));
            return;
        }
        Long userId = WsTokenUtils.getUserId(msgVo.getToken());
        ChannelUtils.setUserChannel(userId, msgVo.getToken(), channel);
        MsgUtils.writeJson(channel, Message.success(msgVo.getType()));
    }

    @Async
    public void srHandler(Channel channel, MsgVo msgVo) {
        log.info("轮询回调客户端登录");
        if (!RedisUtils.hasKey(Constants.SR_LOGIN_KEY + msgVo.getToken())) {
            MsgUtils.writeJson(channel, Message.error(MsgType.ERROR, CodeEnum.NO_TOKEN));
            return;
        }
        Long srId = Long.parseLong(String.valueOf(RedisUtils.get(Constants.SR_LOGIN_KEY + msgVo.getToken())));
        ChannelUtils.setSrClientChannel(srId, msgVo.getToken(), channel);
        MsgUtils.writeJson(channel, Message.success(msgVo.getType()));
    }

}

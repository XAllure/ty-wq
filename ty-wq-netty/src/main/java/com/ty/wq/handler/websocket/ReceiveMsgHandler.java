package com.ty.wq.handler.websocket;

import com.alibaba.fastjson.JSON;
import com.ty.wq.pojo.vo.client.wechatMessage.ReceiveMsg;
import com.ty.wq.pojo.vo.netty.Message;
import com.ty.wq.pojo.vo.netty.MsgVo;
import com.ty.wq.utils.ChannelUtils;
import com.ty.wq.utils.MsgUtils;
import com.ty.wq.utils.OrikaUtils;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 微信消息回调
 * @author Administrator
 */
@Service
@Slf4j
public class ReceiveMsgHandler {

    /**
     * 微信消息回调
     * @param msgVo
     */
    @Async
    public void handler(MsgVo msgVo) {
        ReceiveMsg receiveMsg = OrikaUtils.convert(msgVo.getData(), ReceiveMsg.class);
        log.info(String.valueOf(receiveMsg));
        // cwxid不为空
        if (StringUtils.isNotBlank(receiveMsg.getCwxid())) {
            List<Channel> channels = ChannelUtils.getChannelsByWechatId(receiveMsg.getCwxid());
            if (channels != null) {
                for (Channel channel : channels) {
                    msgVo.setType(receiveMsg.getAction());
                    // 后续再进行详细处理
                    //MsgUtils.writeJson(channel, Message.success(msgVo));
                }
            }
        }
    }

}

package com.ty.wq.handler;

import com.ty.wq.constant.MsgType;
import com.ty.wq.enums.CodeEnum;
import com.ty.wq.handler.websocket.ReceiveMsgHandler;
import com.ty.wq.pojo.vo.netty.Message;
import com.ty.wq.pojo.vo.netty.MsgVo;
import com.ty.wq.utils.MsgUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 */
@Service
@Slf4j
public class WebSocketMsgHandler {

    @Autowired
    private ReceiveMsgHandler receiveMsgHandler;

    public void handler(ChannelHandlerContext ctx, MsgVo msgVo) {
        if (msgVo != null) {
            try {
                Channel channel = ctx.channel();
                switch (msgVo.getType()) {
                    // 心跳
                    case MsgType.HEART_BEAT: {
                        log.info("HEARTBEAT 操作");
                        break;
                    }
                    case MsgType.RECEIVED: {
                        log.info("RECEIVED 操作");
                        break;
                    }
                    // 接收WeQuick客户端返回的信息
                    case MsgType.RECEIVE_MSG: {
                        receiveMsgHandler.handler(channel, msgVo);
                        break;
                    }
                    default: {
                        break;
                    }
                }
            } catch (Exception e) {
                MsgUtils.writeJson(ctx.channel(), Message.error(MsgType.ERROR, CodeEnum.ERROR_SERVER));
            }
        } else {
            MsgUtils.writeJson(ctx.channel(), Message.error(MsgType.ERROR, CodeEnum.ERROR_PARAMETER));
        }
    }

}

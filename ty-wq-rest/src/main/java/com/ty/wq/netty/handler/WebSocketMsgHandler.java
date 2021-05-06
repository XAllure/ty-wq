package com.ty.wq.netty.handler;

import com.ty.wq.constant.MsgType;
import com.ty.wq.enums.ResultEnum;
import com.ty.wq.pojo.vo.netty.Message;
import com.ty.wq.pojo.vo.netty.MsgVo;
import com.ty.wq.utils.ChannelUtils;
import com.ty.wq.utils.MsgUtils;
import com.ty.wq.utils.WsTokenUtils;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 */
@Service
@Slf4j
public class WebSocketMsgHandler {


    public void handler(ChannelHandlerContext ctx, MsgVo msgVo) {
        if (msgVo != null) {
            try {
                switch (msgVo.getType()) {
                    case MsgType.LOGIN: {
                        log.info("LOGIN 操作， 跳过");
                        break;
                    }
                    case MsgType.HEART_BEAT: {
                        log.info("HEARTBEAT 操作");
                        break;
                    }
                    case MsgType.RECEIVED: {
                        log.info("RECEIVED 操作");
                        break;
                    }
                    default: {
                        break;
                    }
                }
            } catch (Exception e) {
                MsgUtils.writeJson(ctx.channel(), Message.error(MsgType.ERROR, ResultEnum.ERROR_SERVER));
            }
        } else {
            MsgUtils.writeJson(ctx.channel(), Message.error(MsgType.ERROR, ResultEnum.ERROR_PARAMETER));
        }
    }

}

package com.ty.wq.netty.handler;

import com.ty.wq.constant.MsgType;
import com.ty.wq.enums.CodeEnum;
import com.ty.wq.pojo.vo.netty.Message;
import com.ty.wq.pojo.vo.netty.MsgVo;
import com.ty.wq.utils.ChannelUtils;
import com.ty.wq.utils.HttpUtils;
import com.ty.wq.utils.MsgUtils;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedHashMap;

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
                        log.info("跳过 LOGIN 操作");
                        break;
                    }
                    case MsgType.HEART_BEAT: {
                        log.info("HEARTBEAT 操作");
                        break;
                    }
                    case MsgType.RECEIVED: {
                        log.info("RECEIVED 操作");
                        // 测试两个服务器之间使用http进行通信
                        Long userId = ChannelUtils.getUserId(ctx.channel());
                        String url = HttpUtils.url(userId, "/system/search?username=墨染红尘&startTime=2021-04-28 15:50:19&endTime=2021-05-06 15:50:19&pageNum=1&pageSize=12");
                        HttpUtils.post(url, new LinkedHashMap<>());
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

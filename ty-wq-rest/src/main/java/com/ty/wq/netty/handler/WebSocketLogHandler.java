package com.ty.wq.netty.handler;

import com.alibaba.fastjson.JSONObject;
import com.ty.wq.constant.MsgType;
import com.ty.wq.pojo.vo.netty.Message;
import com.ty.wq.utils.OrikaUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.stream.ChunkedWriteHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Administrator
 */
@Slf4j
public class WebSocketLogHandler extends ChunkedWriteHandler {

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        if (msg instanceof TextWebSocketFrame){
            String text = ((TextWebSocketFrame) msg).text();
            JSONObject object = JSONObject.parseObject(text);
            Message message = OrikaUtils.convert(object, Message.class);
            log.info(String.valueOf(message));
            // 排除应答日志
            if (!MsgType.RECEIVED.equalsIgnoreCase(message.getType())){
                log.info("webSocket[channelId-"+ ctx.channel().id().asShortText() +"]返回：" + text);
                log.info("----------------------- webSocket返回结束 -------------------");
            }
        }
        super.write(ctx,msg,promise);
    }

}

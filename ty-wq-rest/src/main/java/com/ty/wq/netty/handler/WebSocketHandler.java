package com.ty.wq.netty.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ty.wq.constant.MsgType;
import com.ty.wq.pojo.vo.netty.Message;
import com.ty.wq.utils.OrikaUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Administrator
 */
@Service
@Sharable
@Slf4j
public class WebSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    @Autowired
    private WebSocketMsgHandler webSocketMsgHandler;

    /** 连接数 */
    private static final AtomicInteger CON_COUNT = new AtomicInteger(0);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) {
        log.debug("------------------- webSocket读取并处理消息 ------------------------");
        // 打印请求日志
        String text = msg.text();
        JSONObject object = JSON.parseObject(text);
        Message message = OrikaUtils.convert(object, Message.class);
        if (!MsgType.HEART_BEAT.equals(message.getType())) {
            log.info("WebSocket[channelId-{}]请求参数", ctx.channel().id().asLongText());
            log.info("IP: {}", ctx.channel().remoteAddress());
            log.info("Parameter: {}", message);
        }
        webSocketMsgHandler.handler(ctx, message);
        ctx.flush();
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        // 打印出channel唯一值，asLongText方法是channel的id的全名
        CON_COUNT.incrementAndGet();
        log.info("用户 {} 连接服务器", ctx.channel().id().asLongText());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) {
        CON_COUNT.decrementAndGet();
        log.info("用户 {} 断开服务器连接", ctx.channel().id().asLongText());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        Channel channel = ctx.channel();
        log.error("客户端[" + channel.remoteAddress() + "]异常");
        log.error("异常原因：" + cause);
        cause.printStackTrace();
        //
        channel.close();
    }
}

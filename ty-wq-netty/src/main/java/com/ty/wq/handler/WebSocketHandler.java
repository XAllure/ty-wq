package com.ty.wq.handler;

import com.ty.wq.constant.MsgType;
import com.ty.wq.pojo.vo.netty.MsgVo;
import com.ty.wq.utils.ChannelUtils;
import com.ty.wq.utils.HttpUtils;
import com.ty.wq.utils.MsgUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
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
    private static final AtomicInteger CONNECT_COUNT = new AtomicInteger(0);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) {
        log.debug("------------------------------ webSocket读取并处理消息 -----------------------------------");
        // 打印请求日志
        MsgVo msgVo = MsgUtils.msgVo(msg);
        if (!msgVo.getType().equals(MsgType.HEART_BEAT)) {
            log.info("WebSocket[channelId-{}]请求参数", ctx.channel().id().asLongText());
            log.info("IP: {}", ctx.channel().remoteAddress());
            log.info("Parameter: {}", msgVo);
        }
        webSocketMsgHandler.handler(ctx, msgVo);
        ctx.flush();
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        // 打印出channel唯一值，asLongText方法是channel的id的全名
        CONNECT_COUNT.incrementAndGet();
        log.info("用户[{}]连接服务器", ctx.channel().id().asLongText());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) {
        CONNECT_COUNT.decrementAndGet();
        log.info("用户[{}]断开服务器连接", ctx.channel().id().asLongText());
        ChannelUtils.exit(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        String token = ChannelUtils.getToken(ctx.channel());
        if (StringUtils.isBlank(token)) {
            return;
        }
        Long userId = ChannelUtils.getUserId(ctx.channel());
        String url = HttpUtils.url(userId, "/system/logout/" + token);
        System.out.println(url);
        HttpUtils.post(url, new LinkedHashMap<>());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        Channel channel = ctx.channel();
        log.error("客户端[" + channel.remoteAddress() + "]异常");
        log.error("异常原因：" + cause);
        cause.printStackTrace();
        ctx.close();
    }
}

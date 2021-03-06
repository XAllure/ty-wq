package com.ty.wq.handler;

import com.ty.wq.constant.Action;
import com.ty.wq.constant.MsgType;
import com.ty.wq.pojo.vo.client.wechatMessage.SendMsg;
import com.ty.wq.pojo.vo.netty.Message;
import com.ty.wq.socket.WebSocketClient;
import com.ty.wq.task.GetWechatSomeInfoTask;
import com.ty.wq.utils.MsgUtils;
import com.ty.wq.utils.QueueUtils;
import io.netty.channel.*;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 */
@Service
@Slf4j
public class SocketClientHandler extends SimpleChannelInboundHandler<Object> {

    @Autowired
    private WebSocketClient webSocketClient;

    //握手的状态信息
    private WebSocketClientHandshaker handshaker;

    //netty自带的异步处理
    private ChannelPromise handshakeFuture;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) {
        if (!this.handshaker.isHandshakeComplete()) {
            log.info("当前握手的状态: {}", this.handshaker.isHandshakeComplete());
        }
        Channel ch = ctx.channel();
        FullHttpResponse response;
        //进行握手操作
        if (!this.handshaker.isHandshakeComplete()) {
            try {
                response = (FullHttpResponse)msg;
                //握手协议返回，设置结束握手
                this.handshaker.finishHandshake(ch, response);
                //设置成功
                this.handshakeFuture.setSuccess();
                log.info("服务端的消息: {}", response.headers());
            } catch (WebSocketHandshakeException var7) {
                FullHttpResponse res = (FullHttpResponse)msg;
                String errorMsg = String.format("握手失败,status:%s,reason:%s", res.status(), res.content().toString(CharsetUtil.UTF_8));
                this.handshakeFuture.setFailure(new Exception(errorMsg));
            }
        } else if (msg instanceof FullHttpResponse) {
            response = (FullHttpResponse)msg;
            throw new IllegalStateException("Unexpected FullHttpResponse (getStatus=" + response.status() +
                    ", content=" + response.content().toString(CharsetUtil.UTF_8) + ')');
        }else {
            //接收服务端的消息
            WebSocketFrame frame = (WebSocketFrame)msg;
            // 打印请求日志
            //文本信息
            if (frame instanceof TextWebSocketFrame) {
                Message message = MsgUtils.message((TextWebSocketFrame)msg);
                // 心跳
                if (message.getType().equals(MsgType.HEART_BEAT)) {
                    log.debug("心跳返回接收");
                    return;
                }
                log.info("------------------------------ webSocket读取并处理消息 -----------------------------------");
                log.info("Parameter: {}", message);
                // 微信消息轮询
                if (message.getType().equals(MsgType.SEND_MSG)) {
                    SendMsg sendMsg = MsgUtils.convert(message.getData(), SendMsg.class);
                    QueueUtils.messages.offer(sendMsg);
                    return;
                }
                if (message.getType().equals(Action.REPORT_LOGIN_USER)) {
                    GetWechatSomeInfoTask.wechatId = String.valueOf(message.getData());
                    return;
                }
            }
            //二进制信息
            if (frame instanceof BinaryWebSocketFrame) {
                BinaryWebSocketFrame binFrame = (BinaryWebSocketFrame)frame;
                System.out.println("BinaryWebSocketFrame");
            }
            //ping信息
            if (frame instanceof PongWebSocketFrame) {
                System.out.println("WebSocket Client received pong");
            }
            //关闭消息
            if (frame instanceof CloseWebSocketFrame) {
                System.out.println("receive close frame");
                ch.close();
            }

        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("客户端{}连接", ctx.channel().id().asShortText());
        //super.channelActive(ctx);
        //webSocketClient.loginServer();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("客户端{}断开连接", ctx.channel().id().asShortText());
        webSocketClient.logout();
        super.channelInactive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        Channel channel = ctx.channel();
        log.error("客户端[" + channel.remoteAddress() + "]异常");
        log.error("异常原因：" + cause);
        cause.printStackTrace();
        ctx.close();
        super.exceptionCaught(ctx, cause);
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        this.handshakeFuture = ctx.newPromise();
    }

    public WebSocketClientHandshaker getHandshaker() {
        return handshaker;
    }

    public void setHandshaker(WebSocketClientHandshaker handshaker) {
        this.handshaker = handshaker;
    }

    public ChannelPromise getHandshakeFuture() {
        return handshakeFuture;
    }

    public void setHandshakeFuture(ChannelPromise handshakeFuture) {
        this.handshakeFuture = handshakeFuture;
    }

    public ChannelFuture handshakeFuture() {
        return this.handshakeFuture;
    }
}

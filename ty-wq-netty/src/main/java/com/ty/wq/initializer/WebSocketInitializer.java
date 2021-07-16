package com.ty.wq.initializer;

import com.ty.wq.handler.*;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 */
@Service
public class WebSocketInitializer extends ChannelInitializer<SocketChannel> {

    @Autowired
    private WebSocketHandler webSocketHandler;

    @Autowired
    private WebSocketAuthHandler webSocketAuthHandler;

    @Override
    protected void initChannel(SocketChannel socketChannel) {
        ChannelPipeline pipeline = socketChannel.pipeline();

        // websocket协议本身是基于http协议的，所以这边也要使用http解编码器
        pipeline.addLast(new HttpServerCodec())
                //是以块方式写的，需要添加 ChunkedWriteHandler 处理器
                .addLast(new ChunkedWriteHandler())
                // netty是基于分段请求的，HttpObjectAggregator的作用是将请求分段再聚合,参数是聚合字节的最大长度
                .addLast(new HttpObjectAggregator(65536))
                .addLast(new WebSocketServerProtocolHandler("/", null, false, 65536 * 10))
                .addLast(new WebSocketLogHandler())
                // 自定义验证的 handler
                .addLast(webSocketAuthHandler)
                // 添加自定义的 handler
                .addLast(webSocketHandler);
    }
}

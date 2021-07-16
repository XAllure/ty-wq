package com.ty.wq.initializer;

import com.ty.wq.handler.SocketClientHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 */
@Service
@Slf4j
public class SocketClientInitializer extends ChannelInitializer<SocketChannel> {

    @Autowired
    private SocketClientHandler socketClientHandler;

    @Override
    protected void initChannel(SocketChannel socketChannel) {

        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast(new HttpClientCodec())
                //是以块方式写的，需要添加 ChunkedWriteHandler 处理器
                //.addLast(new ChunkedWriteHandler())
                // netty是基于分段请求的，HttpObjectAggregator的作用是将请求分段再聚合,参数是聚合字节的最大长度
                .addLast(new HttpObjectAggregator(65536))
                .addLast(socketClientHandler);
    }
}

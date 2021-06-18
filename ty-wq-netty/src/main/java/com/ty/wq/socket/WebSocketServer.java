package com.ty.wq.socket;

import com.ty.wq.config.netty.NettyInfoConfig;
import com.ty.wq.initializer.WebSocketInitializer;
import com.ty.wq.utils.NettyUtils;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.threads.ThreadPoolExecutor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author Administrator
 */
@Component
@Slf4j
public class WebSocketServer {

    ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(5, 10, 100, TimeUnit.SECONDS, new LinkedBlockingQueue<>(10));

    @Autowired
    private NettyInfoConfig config;

    @Autowired
    private WebSocketInitializer webSocketInitializer;

    @Bean
    private InitializingBean initializingBean() {
        return this::run;
    }

    private void run() {
        poolExecutor.execute(() -> {
            // 用于处理服务器端接收客户端连接
            EventLoopGroup boosGroup = new NioEventLoopGroup();
            // 用于进行网络通信（网络读写）
            EventLoopGroup workGroup = new NioEventLoopGroup();
            try {
                // 用于服务器通道的一系列配置
                ServerBootstrap bootstrap = new ServerBootstrap();
                bootstrap.group(boosGroup, workGroup)
                        // 指定NIO的模式
                        .channel(NioServerSocketChannel.class)
                        // 保持连接
                        .childOption(ChannelOption.SO_KEEPALIVE, true)
                        .childHandler(webSocketInitializer);
                // 绑定端口
                ChannelFuture cf = bootstrap.bind(config.getNPort()).sync();
                log.info("netty启动成功=====websocket占用端口：{}",  config.getNPort());
                // 注册服务端信息
                NettyUtils.regToRedis(config);
                // 等待服务端监听端口关闭
                cf.channel().closeFuture().sync();
            } catch (Exception e) {
                log.info("netty启动失败");
                e.printStackTrace();
            } finally {
                boosGroup.shutdownGracefully();
                workGroup.shutdownGracefully();
            }
        });
    }
}

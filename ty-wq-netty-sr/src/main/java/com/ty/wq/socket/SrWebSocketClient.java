package com.ty.wq.socket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ty.wq.constant.MsgType;
import com.ty.wq.handler.SrSocketClientHandler;
import com.ty.wq.initializer.SrSocketClientInitializer;
import com.ty.wq.pojo.vo.Result;
import com.ty.wq.pojo.vo.client.user.LoginReqVo;
import com.ty.wq.pojo.vo.netty.MsgVo;
import com.ty.wq.pojo.vo.netty.WsServer;
import com.ty.wq.utils.HttpUtils;
import com.ty.wq.utils.OrikaUtils;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshakerFactory;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.tomcat.util.threads.ThreadPoolExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author Administrator
 */
@Service
@Slf4j
public class SrWebSocketClient {

    ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(5, 10, 100, TimeUnit.SECONDS, new LinkedBlockingQueue<>(10));

    @Autowired
    private SrSocketClientInitializer srSocketClientInitializer;

    public static WsServer server;

    public static Channel channel;

    public static String token;

    public static final String URL = "http://127.0.0.1:20010";

    @PostConstruct
    private void start() {
        run();
    }

    public void run() {
        login();
        poolExecutor.execute(() -> {
            EventLoopGroup group = new NioEventLoopGroup();
            try {
                Bootstrap bootstrap = new Bootstrap();
                bootstrap.group(group)
                        .channel(NioSocketChannel.class)
                        .option(ChannelOption.SO_KEEPALIVE, true)
                        .option(ChannelOption.TCP_NODELAY, true)
                        .handler(new LoggingHandler(LogLevel.INFO))
                        .handler(srSocketClientInitializer);
                URI wsUri = new URI("ws://" + server.getPip() + ":" + server.getNport() + "/");
                HttpHeaders httpHeaders = new DefaultHttpHeaders();
                //进行握手
                WebSocketClientHandshaker handshaker = WebSocketClientHandshakerFactory.newHandshaker(wsUri,
                        WebSocketVersion.V13, null, true, httpHeaders, 65536 * 10);

                ChannelFuture future = bootstrap.connect(wsUri.getHost(), wsUri.getPort()).sync();
                log.info("netty客户端启动成功， 连接端口为：{}", server.getNport());
                channel = future.channel();
                SrSocketClientHandler handler = channel.pipeline().get(SrSocketClientHandler.class);
                handler.setHandshaker(handshaker);
                handshaker.handshake(channel);
                //阻塞等待是否握手成功
                handler.handshakeFuture().sync();
                log.info("握手成功");
                //给服务端发送的内容，如果客户端与服务端连接成功后，可以多次调用这个方法发送消息
                loginServer();
                channel.closeFuture().sync();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                group.shutdownGracefully();
            }
        });
    }

    public void login() {
        LoginReqVo loginReqVo = new LoginReqVo();
        loginReqVo.setUsername("sr001");
        loginReqVo.setPassword(DigestUtils.md5Hex("123456"));
        JSONObject json = OrikaUtils.convert(loginReqVo, JSONObject.class);
        String res = HttpUtils.post(URL.concat("/system/sr/login"), json);
        JSONObject jsonObject = JSON.parseObject(res);
        Result result = OrikaUtils.convert(jsonObject, Result.class);
        if (result.getCode() == 0) {
            JSONObject data = JSON.parseObject(String.valueOf(result.getData()));
            String token = data.getString("token");
            SrWebSocketClient.token = token;
            log.info("获取token：" + token);
            server = OrikaUtils.convert(data.getJSONObject("server"), WsServer.class);
            log.info("获取server：" + server);
        }
    }

    public void loginServer() {
        MsgVo msgVo = new MsgVo();
        msgVo.setToken(token);
        msgVo.setType(MsgType.SR_LOGIN);
        JSONObject obj = OrikaUtils.convert(msgVo, JSONObject.class);
        channel.writeAndFlush(new TextWebSocketFrame(obj.toJSONString()));
        log.info("正在登录服务器......");
    }

    public void logout() {
        String url = URL + "/system/sr/logout";
        String res = HttpUtils.post(url, token, new JSONObject());
        log.info("退出返回：" + res);
        clear();
    }

    public void clear() {
        channel.close();
        server = null;
    }

}
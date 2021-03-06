package com.ty.wq.socket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ty.wq.handler.SocketClientHandler;
import com.ty.wq.initializer.SocketClientInitializer;
import com.ty.wq.pojo.vo.Result;
import com.ty.wq.pojo.vo.client.user.LoginReqVo;
import com.ty.wq.pojo.vo.client.user.UserRespVo;
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
import org.apache.commons.lang.StringUtils;
import org.apache.tomcat.util.threads.ThreadPoolExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.util.Scanner;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author Administrator
 */
@Service
@Slf4j
public class WebSocketClient {

    ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(5, 10, 100, TimeUnit.SECONDS, new LinkedBlockingQueue<>(10));

    @Autowired
    private SocketClientInitializer socketClientInitializer;

    public static WsServer server;

    public static UserRespVo user;

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
                        .handler(socketClientInitializer);
                URI wsUri = new URI("ws://" + server.getPip() + ":" + server.getNport() + "/");
                HttpHeaders httpHeaders = new DefaultHttpHeaders();
                //????????????
                WebSocketClientHandshaker handshaker = WebSocketClientHandshakerFactory.newHandshaker(wsUri,
                        WebSocketVersion.V13, null, true, httpHeaders, 65536 * 10);

                ChannelFuture future = bootstrap.connect(wsUri.getHost(), wsUri.getPort()).sync();
                log.info("netty???????????????????????? ??????????????????{}", server.getNport());
                channel = future.channel();
                SocketClientHandler handler = channel.pipeline().get(SocketClientHandler.class);
                handler.setHandshaker(handshaker);
                handshaker.handshake(channel);
                //??????????????????????????????
                handler.handshakeFuture().sync();
                log.info("????????????");
                //?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
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
        LoginReqVo vo = new LoginReqVo();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.println("??????????????????");
                String username = scanner.nextLine();
                System.out.println("???????????????");
                String password = scanner.nextLine();
                if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
                    continue;
                }
                vo.setUsername(username);
                vo.setPassword(DigestUtils.md5Hex(password));
                JSONObject json = OrikaUtils.convert(vo, JSONObject.class);
                String res = HttpUtils.post(URL.concat("/system/login"), json);
                JSONObject jsonObject = JSON.parseObject(res);
                Result result = OrikaUtils.convert(jsonObject, Result.class);
                if (result.getCode() == 0) {
                    JSONObject data = JSON.parseObject(String.valueOf(result.getData()));
                    String token = data.getString("token");
                    WebSocketClient.token = token;
                    log.info("??????token???" + token);
                    server = OrikaUtils.convert(data.getJSONObject("server"), WsServer.class);
                    log.info("??????server???" + server);
                    user = data.getJSONObject("user").toJavaObject(UserRespVo.class);
                    log.info("??????user???" + user);
                    break;
                } else {
                    System.out.println(result.getMsg());
                }
            } catch (Exception e) {
                log.info("?????????");
                e.printStackTrace();
            }
        }
    }

    public void loginServer() {
        MsgVo msgVo = new MsgVo();
        msgVo.setToken(token);
        msgVo.setType("Login");
        JSONObject obj = OrikaUtils.convert(msgVo, JSONObject.class);
        channel.writeAndFlush(new TextWebSocketFrame(obj.toJSONString()));
        log.info("?????????????????????......");
    }

    public void logout() {
        String url = URL + "/system/logout";
        String res = HttpUtils.post(url, token, new JSONObject());
        log.info("???????????????" + res);
        clear();
    }

    public void clear() {
        channel.close();
        server = null;
        user = null;
    }

}

package com.ty.wq.utils;

import com.ty.wq.config.netty.NettyInfoConfig;
import com.ty.wq.constant.Constants;
import com.ty.wq.pojo.vo.netty.WsServer;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.util.concurrent.TimeUnit;

/**
 * @author Administrator
 */
@Component
@Slf4j
public class NettyUtils {

    /**
     * 向 redis 注册服务器信息
     * @param config netty 配置信息
     */
    @SneakyThrows
    public static void regToRedis(NettyInfoConfig config) {
        String innerIp = InetAddress.getLocalHost().getHostAddress();
        WsServer wsServer = new WsServer(config.getId(), config.getPIp(), innerIp, config.getNPort(),config.getHPort());
        RedisUtils.setValueSeconds(Constants.WS_SERVER_INFO.concat(config.getId()), wsServer, Constants.REG_TIMEOUT);
    }

    /**
     * 向 redis 重新注册服务器信息
     * @param config netty 配置信息
     */
    public static void reRegToRedis(NettyInfoConfig config) {
        log.info("---------------------------------------------------------------------");
        if (RedisUtils.hasKey(Constants.WS_SERVER_INFO.concat(config.getId()))) {
            RedisUtils.setExpire(Constants.WS_SERVER_INFO.concat(config.getId()), Constants.REG_TIMEOUT, TimeUnit.SECONDS);
            log.info("NettyWebSocketServer 存在，重置 NettyWebSocketServer 时效");
        } else {
            regToRedis(config);
            log.info("NettyWebSocketServer 不存在，重新注册 NettyWebSocketServer");
        }
        log.info("---------------------------------------------------------------------");
    }


}

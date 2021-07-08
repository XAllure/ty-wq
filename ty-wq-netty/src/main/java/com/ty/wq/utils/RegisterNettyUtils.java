package com.ty.wq.utils;

import com.ty.wq.config.NettyConfig;
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
public class RegisterNettyUtils {

    /**
     * 向 redis 注册服务器信息
     * @param config netty 配置信息
     */
    @SneakyThrows
    public static void regToRedis(NettyConfig config) {
        String innerIp = InetAddress.getLocalHost().getHostAddress();
        WsServer wsServer = new WsServer(config.getId(), config.getPip(), innerIp, config.getNport(),config.getHport());
        RedisUtils.setSeconds(Constants.WQ_SERVER_INFO.concat(config.getId()), wsServer, Constants.REG_TIMEOUT);
    }

    /**
     * 向 redis 重新注册服务器信息
     * @param config netty 配置信息
     */
    public static void reRegToRedis(NettyConfig config) {
        if (RedisUtils.hasKey(Constants.WQ_SERVER_INFO.concat(config.getId()))) {
            RedisUtils.setExpire(Constants.WQ_SERVER_INFO.concat(config.getId()), Constants.REG_TIMEOUT, TimeUnit.SECONDS);
        } else {
            regToRedis(config);
        }
    }


}

package com.ty.wq.config;

import com.ty.wq.constant.Constants;
import com.ty.wq.pojo.vo.netty.Route;
import com.ty.wq.utils.RedisUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

/**
 * @author Administrator
 */
@Component
@Data
@Slf4j
public class RouteConfig {

    @Value("${custom.route.id}")
    private String id;

    private String ip;

    @Value("${server.port}")
    private Integer port;

    public RouteConfig() throws UnknownHostException {
        this.ip = InetAddress.getLocalHost().getHostAddress();
    }

    @Bean
    private InitializingBean initializingBean() {
        return this::regToRedis;
    }

    public void regToRedis() {
        Route route = new Route(this.id, this.ip, this.port);
        RedisUtils.setSeconds(Constants.WQ_ROUTE_INFO.concat(this.id), route, Constants.REG_TIMEOUT);
    }

    public void reRegToRedis() {
        if (RedisUtils.hasKey(Constants.WQ_ROUTE_INFO.concat(this.id))) {
            RedisUtils.setExpire(Constants.WQ_ROUTE_INFO.concat(this.id), Constants.REG_TIMEOUT, TimeUnit.SECONDS);
        } else {
            regToRedis();
        }
    }

}

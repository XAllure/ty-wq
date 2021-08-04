package com.ty.wq.task;

import com.ty.wq.config.RouteConfig;
import com.ty.wq.constant.Constants;
import com.ty.wq.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author Administrator
 */
@Component
@Slf4j
public class TaskManager {

    @Autowired
    private RouteConfig routeConfig;

    @Async
    @Scheduled(cron = "0 0/1 * * * ?")
    public void reReg(){
        routeConfig.reRegToRedis();
        log.info("----------------------------------------------------------------------------------------------------------------------------");
        log.info("路由的key{}", RedisUtils.getAllKeys(Constants.WQ_ROUTE_INFO + "*"));
        log.info("用户登录的key{}", RedisUtils.getAllKeys(Constants.WQ_USER_LOGIN_KEY + "*"));
        log.info("轮询回调客户端登录的key{}", RedisUtils.getAllKeys(Constants.SR_LOGIN_KEY + "*"));
        log.info("----------------------------------------------------------------------------------------------------------------------------");
    }

}

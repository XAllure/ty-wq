package com.ty.wq.task;

import com.ty.wq.config.netty.NettyInfoConfig;
import com.ty.wq.constant.Constants;
import com.ty.wq.utils.NettyUtils;
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
    private NettyInfoConfig config;

    @Async
    @Scheduled(cron = "0 0/1 * * * ?")
    public void reReg(){
        NettyUtils.reRegToRedis(config);
        log.info("----------------------------------------------------------------------------------------------------------------------------");
        log.info("服务器的key{}", RedisUtils.getAllKeys(Constants.WS_SERVER_INFO + "*"));
        log.info("用户登录的key{}", RedisUtils.getAllKeys(Constants.WQ_LOGIN_KEY + "*"));
        log.info("用户服务器的key{}", RedisUtils.getAllKeys(Constants.WS_USER_SERVER + "*"));
        log.info("----------------------------------------------------------------------------------------------------------------------------");
    }

}

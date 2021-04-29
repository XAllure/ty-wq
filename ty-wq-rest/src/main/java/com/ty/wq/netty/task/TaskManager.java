package com.ty.wq.netty.task;

import com.ty.wq.config.netty.NettyInfoConfig;
import com.ty.wq.constant.Constants;
import com.ty.wq.utils.NettyUtils;
import com.ty.wq.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Set;

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
    }

}

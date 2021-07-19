package com.ty.wq.task;

import com.ty.wq.constant.MsgType;
import com.ty.wq.pojo.vo.netty.MsgVo;
import com.ty.wq.socket.WebSocketClient;
import com.ty.wq.utils.MsgUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 心跳任务
 * @author Administrator
 */
@Component
public class HeartBeatTask {

    /**
     * 每三秒执行一次心跳
     */
    @Async
    @Scheduled(cron = "0/3 * * * * ?")
    public void heartBeat() {
        MsgVo msgVo = new MsgVo();
        msgVo.setType(MsgType.HEART_BEAT);
        MsgUtils.writeJson(WebSocketClient.channel, msgVo);
    }

}

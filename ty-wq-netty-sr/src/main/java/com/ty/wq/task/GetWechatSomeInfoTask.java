package com.ty.wq.task;

import com.ty.wq.constant.ApiType;
import com.ty.wq.pojo.vo.client.wechatMessage.SendMsg;
import com.ty.wq.utils.QueueUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author Administrator
 */
@Component
@Slf4j
public class GetWechatSomeInfoTask {

    public static String wechatId;

    /**
     * 获取微信普通好友
     * @param wechatId
     */
    private void getWechatFriend(String wechatId) {
        SendMsg sMsg = new SendMsg();
        sMsg.setApi(ApiType.GET_CONTACTS);
        sMsg.setSendId(wechatId);
        QueueUtils.messages.add(sMsg);
    }

    /**
     * 获取微信普通群
     * @param wechatId
     */
    private void getWechatRooms(String wechatId) {
        SendMsg sMsg = new SendMsg();
        sMsg.setApi(ApiType.GET_CHAT_ROOMS);
        sMsg.setSendId(wechatId);
        QueueUtils.messages.add(sMsg);
    }



    /**
     * 每2小时(7200000毫秒)获取一次登录微信的好友与群列表
     */
    @Async
    @Scheduled(fixedDelay = 7200000)
    public void get() {
        if (StringUtils.isNotBlank(wechatId)) {
            log.info("获取登录微信【{}】的好友与群列表", wechatId);
            getWechatFriend(wechatId);
            getWechatRooms(wechatId);
        }
    }

}

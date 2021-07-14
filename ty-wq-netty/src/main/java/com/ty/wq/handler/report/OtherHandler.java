package com.ty.wq.handler.report;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ty.wq.pojo.vo.client.wechatMessage.ReceiveMsg;
import com.ty.wq.utils.SendUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 */
@Service
public class OtherHandler {

    /**
     * 上报当前聊天对象改变
     * @param rMsg
     */
    @Async
    public void talkerChangeHandler(ReceiveMsg rMsg) {
        // 后续再改
        JSONObject data = JSON.parseObject(String.valueOf(rMsg.getData()));
        SendUtils.send(rMsg.getCwxid(), rMsg.getAction(), data);
    }

}

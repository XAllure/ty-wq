package com.ty.wq.handler.report;

import com.ty.wq.pojo.vo.client.wechatMessage.ReceiveMsg;
import com.ty.wq.utils.ChannelUtils;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * 上报当前登录微信详细信息
 * @author Administrator
 */
@Service
@Slf4j
public class LoginUserHandler {

    @Async
    public void handler(Channel channel, ReceiveMsg rMsg) {
        // 保存当前微信对应的转发客户端channel
        ChannelUtils.setWechatClientChannel(rMsg.getCwxid(), channel);
        log.info(String.valueOf(rMsg));
        // 将当前微信比如微信信息、好友列表等等同步到数据库（后续操作）
    }

}

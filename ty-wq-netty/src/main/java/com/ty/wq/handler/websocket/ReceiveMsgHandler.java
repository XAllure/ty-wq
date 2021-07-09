package com.ty.wq.handler.websocket;

import com.alibaba.fastjson.JSON;
import com.ty.wq.constant.ActionType;
import com.ty.wq.pojo.vo.client.wechatMessage.ReceiveMsg;
import com.ty.wq.pojo.vo.netty.Message;
import com.ty.wq.pojo.vo.netty.MsgVo;
import com.ty.wq.utils.ChannelUtils;
import com.ty.wq.utils.MsgUtils;
import com.ty.wq.utils.OrikaUtils;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 微信消息回调
 * @author Administrator
 */
@Service
@Slf4j
public class ReceiveMsgHandler {

    /**
     * 微信消息回调
     * @param msgVo
     */
    @Async
    public void handler(Channel channel, MsgVo msgVo) {
        ReceiveMsg rMsg = OrikaUtils.convert(msgVo.getData(), ReceiveMsg.class);
        log.info(String.valueOf(rMsg));
        // 如果是上报当前登录微信详细信息（相当于WeQuick工具登录微信）
        if (rMsg.getAction().equals(ActionType.REPORT_LOGIN_USER)) {
            // 保存当前微信对应的转发客户端channel
            ChannelUtils.setWechatClientChannel(rMsg.getCwxid(), channel);
            // 将当前微信比如微信信息、好友列表等等同步到数据库（后续操作）
        }
        // cwxid不为空
        if (StringUtils.isNotBlank(rMsg.getCwxid())) {
            List<Channel> channels = ChannelUtils.getChannelsByWechatId(rMsg.getCwxid());
            if (channels != null) {
                for (Channel ch : channels) {
                    msgVo.setType(rMsg.getAction());
                    // 后续再进行详细处理
                    MsgUtils.writeJson(ch, Message.success(msgVo));
                }
            }
        }
    }

}

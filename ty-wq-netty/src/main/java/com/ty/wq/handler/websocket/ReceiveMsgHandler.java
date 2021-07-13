package com.ty.wq.handler.websocket;

import com.ty.wq.constant.Action;
import com.ty.wq.handler.report.WechatFriendHandler;
import com.ty.wq.handler.report.LoginUserHandler;
import com.ty.wq.pojo.vo.client.wechatMessage.ReceiveMsg;
import com.ty.wq.pojo.vo.netty.MsgVo;
import com.ty.wq.utils.OrikaUtils;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * 微信消息回调
 * @author Administrator
 */
@Service
@Slf4j
public class ReceiveMsgHandler {

    @Autowired
    private LoginUserHandler loginUserHandler;

    @Autowired
    private WechatFriendHandler wechatFriendHandler;

    /**
     * 微信消息回调
     * @param msgVo
     */
    @Async
    public void handler(Channel channel, MsgVo msgVo) {
        ReceiveMsg rMsg = OrikaUtils.convert(msgVo.getData(), ReceiveMsg.class);
        // cwxid不为空
        if (StringUtils.isNotBlank(rMsg.getCwxid())) {
            switch (rMsg.getAction()) {
                // 上报当前登录微信详细信息（相当于WeQuick工具登录微信）
                case Action.REPORT_LOGIN_USER: {
                    loginUserHandler.handler(channel, rMsg);
                    break;
                }
                // 上报普通好友列表
                case Action.REPORT_CONTACTS: {
                    wechatFriendHandler.friendListHandler(channel, rMsg);
                    break;
                }
                default: {
                    break;
                }
            }
            /*List<Channel> channels = ChannelUtils.getChannelsByWechatId(rMsg.getCwxid());
            if (channels != null) {
                for (Channel ch : channels) {
                    msgVo.setType(rMsg.getAction());
                    // 后续再进行详细处理
                    MsgUtils.writeJson(ch, Message.success(msgVo));
                }
            }*/
        }
    }

}

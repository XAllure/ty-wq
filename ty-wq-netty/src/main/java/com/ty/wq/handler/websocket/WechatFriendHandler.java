package com.ty.wq.handler.websocket;

import com.ty.wq.constant.MsgType;
import com.ty.wq.pojo.vo.netty.MsgVo;
import com.ty.wq.utils.ChannelUtils;
import com.ty.wq.utils.MsgUtils;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


/**
 * 微信好友相关
 * @author Administrator
 */
@Service
@Slf4j
public class WechatFriendHandler {

    /**
     * 获取普通好友列表
     * @param channel
     * @param msgVo
     */
    @Async
    public void getContactsHandler(Channel channel, MsgVo msgVo) {
        send(msgVo);
    }

    /**
     * 获取单个普通好友信息
     * @param channel
     * @param msgVo
     */
    @Async
    public void getSingleContactHandler(Channel channel, MsgVo msgVo) {
        send(msgVo);
    }

    /**
     * 添加好友
     * @param channel
     * @param msgVo
     */
    @Async
    public void addFriendHandler(Channel channel, MsgVo msgVo) {
        send(msgVo);
    }

    /**
     * 接收加好友请求
     * @param channel
     * @param msgVo
     */
    @Async
    public void acceptFriendHandler(Channel channel, MsgVo msgVo) {
        send(msgVo);
    }

    /**
     * 删除好友
     * @param channel
     * @param msgVo
     */
    @Async
    public void delFriendHandler(Channel channel, MsgVo msgVo) {
        send(msgVo);
    }

    /**
     * 修改好友备注
     * @param channel
     * @param msgVo
     */
    @Async
    public void updateRemarkHandler(Channel channel, MsgVo msgVo) {
        send(msgVo);
    }



    /**
     * 往客户端转发器发送消息
     * @param msgVo
     */
    public void send(MsgVo msgVo) {
        msgVo.setType(MsgType.SEND_MSG);
        MsgUtils.writeJson(ChannelUtils.clientChannel(), msgVo);
    }

}

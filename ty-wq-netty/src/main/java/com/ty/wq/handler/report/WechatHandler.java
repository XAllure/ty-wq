package com.ty.wq.handler.report;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ty.wq.constant.ApiType;
import com.ty.wq.constant.MsgType;
import com.ty.wq.pojo.po.client.Wechat;
import com.ty.wq.pojo.vo.client.wechatMessage.ReceiveMsg;
import com.ty.wq.pojo.vo.client.wechatMessage.SendMsg;
import com.ty.wq.pojo.vo.netty.Message;
import com.ty.wq.pojo.vo.netty.MsgVo;
import com.ty.wq.pojo.vo.netty.report.WechatFriendVo;
import com.ty.wq.service.client.WechatService;
import com.ty.wq.utils.ChannelUtils;
import com.ty.wq.utils.MsgUtils;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * 上报当前登录微信详细信息
 * @author Administrator
 */
@Service
@Slf4j
public class WechatHandler {

    @Autowired
    private WechatService wechatService;

    @Async
    public void loginUserHandler(Channel channel, ReceiveMsg rMsg) {
        JSONObject data = JSON.parseObject(String.valueOf(rMsg.getData()));
        WechatFriendVo vo = data.toJavaObject(WechatFriendVo.class);
        // 将当前微信比如微信信息、好友列表等等同步到数据库（后续操作）
        Wechat wechat = wechatService.findByWechatId(vo.getWxid());
        if (wechat != null) {
            // 保存当前微信对应的转发客户端channel
            ChannelUtils.setWechatClientChannel(rMsg.getCwxid(), channel);
            log.info("保存当前微信对应的转发客户端channel[{}]", channel);
            // 同步个人微信信息
            wechat.setWechatNick(vo.getNick());
            wechat.setWechatNo(vo.getAlias());
            wechat.setHeadPic(vo.getHeadPic());
            wechatService.updateById(wechat);
            // 同步好友列表、群等
            getWechatFriend(wechat.getWechatId());
            getWechatRooms(wechat.getWechatId());
        }
    }

    /**
     * 上报退出登录事件
     * @param channel
     * @param rMsg
     */
    @Async
    public void logoutHandler(Channel channel, ReceiveMsg rMsg) {
        log.info("用户[{}]退出微信", rMsg.getCwxid());
        ChannelUtils.delClientByWechatIdAndChannel(rMsg.getCwxid(), channel);
    }

    /**
     * 获取微信普通好友
     * @param wechatId
     */
    private void getWechatFriend(String wechatId) {
        SendMsg sMsg = new SendMsg();
        sMsg.setApi(ApiType.GET_CONTACTS);
        sMsg.setSendId(wechatId);
        send(sMsg);
    }

    /**
     * 获取微信普通群
     * @param wechatId
     */
    private void getWechatRooms(String wechatId) {
        SendMsg sMsg = new SendMsg();
        sMsg.setApi(ApiType.GET_CHAT_ROOMS);
        sMsg.setSendId(wechatId);
        send(sMsg);
    }

    /**
     * 往Netty客户端发送数据
     * @param sMsg
     */
    private void send(SendMsg sMsg) {
        MsgVo msgVo = new MsgVo();
        msgVo.setType(MsgType.SEND_MSG);
        msgVo.setData(sMsg);
        MsgUtils.writeJson(ChannelUtils.getClientChannel(sMsg.getSendId()), Message.success(msgVo));
    }

}

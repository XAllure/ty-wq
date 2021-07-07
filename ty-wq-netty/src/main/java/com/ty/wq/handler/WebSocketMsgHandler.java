package com.ty.wq.handler;

import com.ty.wq.constant.MsgType;
import com.ty.wq.enums.CodeEnum;
import com.ty.wq.handler.websocket.ReceiveMsgHandler;
import com.ty.wq.handler.websocket.SendMsgHandler;
import com.ty.wq.handler.websocket.WechatFriendHandler;
import com.ty.wq.handler.websocket.WechatHandler;
import com.ty.wq.pojo.vo.client.user.UserRespVo;
import com.ty.wq.pojo.vo.netty.Message;
import com.ty.wq.pojo.vo.netty.MsgVo;
import com.ty.wq.service.client.UserService;
import com.ty.wq.utils.ChannelUtils;
import com.ty.wq.utils.MsgUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 */
@Service
@Slf4j
public class WebSocketMsgHandler {

    @Autowired
    private SendMsgHandler sendMsgHandler;

    @Autowired
    private ReceiveMsgHandler receiveMsgHandler;

    @Autowired
    private UserService userService;

    @Autowired
    private WechatHandler wechatHandler;

    @Autowired
    private WechatFriendHandler wechatFriendHandler;

    public void handler(ChannelHandlerContext ctx, MsgVo msgVo) {
        if (msgVo != null) {
            try {
                Channel channel = ctx.channel();
                switch (msgVo.getType()) {
                    // 心跳
                    case MsgType.HEART_BEAT: {
                        log.info("HEARTBEAT 操作");
                        break;
                    }
                    case MsgType.RECEIVED: {
                        log.info("RECEIVED 操作");
                        break;
                    }
                    // ty-wq-netty-test的scanner测试能不能获取到数据
                    case MsgType.MY_INFO: {
                        UserRespVo vo = userService.getById(ChannelUtils.getUserId(ctx.channel()));
                        msgVo.setData(vo);
                        MsgUtils.writeJson(channel, Message.success(msgVo));
                        break;
                    }
                    // 往客户端发送WeQuick要轮询的信息
                    /*case MsgType.SEND_MSG: {
                        sendMsgHandler.handler(channel, msgVo);
                        break;
                    }*/
                    // 接收WeQuick返回的信息
                    case MsgType.RECEIVE_MSG: {
                        receiveMsgHandler.handler(msgVo);
                        break;
                    }
                    // 微信登录
                    case MsgType.WECHAT_LOGIN: {
                        wechatHandler.loginHandler(channel, msgVo);
                        break;
                    }
                    // 获取普通好友列表
                    case MsgType.GET_CONTACTS: {
                        wechatFriendHandler.getContactsHandler(channel, msgVo);
                        break;
                    }
                    // 获取单个普通好友信息
                    case MsgType.GET_SINGLE_CONTACT: {
                        wechatFriendHandler.getSingleContactHandler(channel, msgVo);
                        break;
                    }
                    // 添加好友
                    case MsgType.ADD_FRIEND: {
                        wechatFriendHandler.addFriendHandler(channel, msgVo);
                        break;
                    }
                    // 接收加好友请求
                    case MsgType.ACCEPT_FRIEND: {
                        wechatFriendHandler.acceptFriendHandler(channel, msgVo);
                        break;
                    }
                    // 删除好友
                    case MsgType.DEL_FRIEND: {
                        wechatFriendHandler.delFriendHandler(channel, msgVo);
                        break;
                    }
                    // 修改好友备注
                    case MsgType.UPDATE_REMARK: {
                        wechatFriendHandler.updateRemarkHandler(channel, msgVo);
                        break;
                    }
                    default: {
                        break;
                    }
                }
            } catch (Exception e) {
                MsgUtils.writeJson(ctx.channel(), Message.error(MsgType.ERROR, CodeEnum.ERROR_SERVER));
            }
        } else {
            MsgUtils.writeJson(ctx.channel(), Message.error(MsgType.ERROR, CodeEnum.ERROR_PARAMETER));
        }
    }

}

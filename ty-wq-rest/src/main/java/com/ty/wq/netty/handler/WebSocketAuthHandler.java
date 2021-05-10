package com.ty.wq.netty.handler;

import com.ty.wq.constant.MsgType;
import com.ty.wq.enums.CodeEnum;
import com.ty.wq.netty.handler.websocket.LoginHandler;
import com.ty.wq.pojo.vo.netty.MsgVo;
import com.ty.wq.pojo.vo.netty.Message;
import com.ty.wq.utils.ChannelUtils;
import com.ty.wq.utils.MsgUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 用于验证用户是否登录
 * @author Administrator
 */
@Service
@Sharable
@Slf4j
public class WebSocketAuthHandler extends ChannelInboundHandlerAdapter {

    @Autowired
    private LoginHandler loginHandler;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Channel channel = ctx.channel();
        MsgVo msgVo = MsgUtils.msgVo((TextWebSocketFrame) msg);
        // 如果是登录类型
        if (msgVo.getType().equals(MsgType.LOGIN)) {
            // 进行登录
            loginHandler.handler(channel, msgVo);
            return;
        }
        // 如果是心跳检测
        if (msgVo.getType().equals(MsgType.HEART_BEAT)) {
            // 传给下一个handler处理
            super.channelRead(ctx, msg);
            return;
        }
        // 如果没有登录，则提醒用户登录
        if (StringUtils.isBlank(ChannelUtils.getToken(channel))) {
            log.info("用户[{}]没有登录， 提醒用户登录！！！", channel.id().asLongText());
            MsgUtils.writeJson(channel, Message.error(MsgType.ERROR, CodeEnum.SERVER_NOT_LOGIN));
            return;
        }
        // 否则的话就直接传给下一个 handler 处理, 并删除该 handler（也可不删除，只是重复验证会影响性能）
        // 移除该handler
        channel.pipeline().remove(this);
        // 传给下一个handler处理
        super.channelRead(ctx, msg);
    }


}

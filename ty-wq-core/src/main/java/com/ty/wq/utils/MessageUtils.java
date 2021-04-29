package com.ty.wq.utils;

import com.alibaba.fastjson.JSONObject;
import com.ty.wq.pojo.vo.netty.Message;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

/**
 * netty 发送消息
 * @author Administrator
 */
public class MessageUtils {

    /**
     * 发送消息
     * @param ctx
     * @param message
     */
    public static void write(ChannelHandlerContext ctx, Message message) {
        ctx.channel().writeAndFlush(message);
    }

    /**
     * 发送 json 消息
     * @param ctx
     * @param message
     */
    public static void writeJson(ChannelHandlerContext ctx, Message message) {
        JSONObject obj = OrikaUtils.convert(message, JSONObject.class);
        ctx.channel().writeAndFlush(new TextWebSocketFrame(obj.toJSONString()));
    }

    /**
     * 发送简单的
     * @param ctx
     * @param message
     */
    public static void writeSimpleJson(ChannelHandlerContext ctx, Message message) {
        message.setToken(null);
        writeJson(ctx, message);
    }

}

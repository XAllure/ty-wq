package com.ty.wq.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ty.wq.pojo.vo.client.wechatMessage.SendMsg;
import com.ty.wq.pojo.vo.netty.Message;
import com.ty.wq.pojo.vo.netty.MsgVo;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

/**
 * netty 发送消息
 * @author Administrator
 */
public class MsgUtils {

    /**
     * 返回 MsgVo 类型消息
     * @param msg
     * @return
     */
    public static MsgVo msgVo(TextWebSocketFrame msg) {
        return convert(msg.text(), MsgVo.class);
    }

    /**
     * 返回 Message 类型消息
     * @param msg
     * @return
     */
    public static Message message(TextWebSocketFrame msg) {
        return convert(msg.text(), Message.class);
    }

    /**
     * 实体转换, 字段相同的转换
     * @param source
     * @param target
     * @param <S>
     * @param <D>
     * @return
     */
    public static <S, D> D convert(S source, Class<D> target) {
        JSONObject object = JSON.parseObject(String.valueOf(source));
        return OrikaUtils.convert(object, target);
    }

    /**
     * 发送消息
     * @param channel
     * @param message
     */
    public static void write(Channel channel, Message message) {
        channel.writeAndFlush(message);
    }


    /**
     * 发送 json 消息
     * @param channel
     * @param message
     */
    public static void writeJson(Channel channel, Message message) {
        JSONObject obj = OrikaUtils.convert(message, JSONObject.class);
        channel.writeAndFlush(new TextWebSocketFrame(obj.toJSONString()));
    }

    /**
     * 发送 json 消息
     * @param channel
     * @param msgVo
     */
    public static void writeJson(Channel channel, MsgVo msgVo) {
        JSONObject obj = OrikaUtils.convert(msgVo, JSONObject.class);
        channel.writeAndFlush(new TextWebSocketFrame(obj.toJSONString()));
    }

}

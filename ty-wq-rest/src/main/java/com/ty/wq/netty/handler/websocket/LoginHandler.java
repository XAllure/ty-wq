package com.ty.wq.netty.handler.websocket;

import com.alibaba.fastjson.JSONObject;
import com.ty.wq.pojo.vo.Result;
import com.ty.wq.pojo.vo.netty.Message;
import com.ty.wq.utils.OrikaUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 */
@Service
@Slf4j
public class LoginHandler {

    @Async
    public void handler(ChannelHandlerContext ctx, Message message) {
        message.setData(Result.success("cdsc"));
        JSONObject obj = OrikaUtils.convert(message, JSONObject.class);
        ctx.channel().writeAndFlush(new TextWebSocketFrame(obj.toJSONString()));
    }

}

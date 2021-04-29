package com.ty.wq.netty.handler;

import com.alibaba.fastjson.JSONObject;
import com.ty.wq.enums.ResultEnum;
import com.ty.wq.netty.handler.websocket.LoginHandler;
import com.ty.wq.pojo.vo.Result;
import com.ty.wq.pojo.vo.netty.Message;
import com.ty.wq.utils.OrikaUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
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
    private LoginHandler loginHandler;


    public void handler(ChannelHandlerContext ctx, Message message) {
        if (message != null) {
            try {
                loginHandler.handler(ctx, message);
                //ctx.channel().writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(message)));
            } catch (Exception e) {
                message.setData(Result.error(ResultEnum.ERROR_PARAMETER));
                JSONObject obj = OrikaUtils.convert(message, JSONObject.class);
                ctx.channel().writeAndFlush(new TextWebSocketFrame(obj.toJSONString()));
                log.error("参数传入错误！");
                e.printStackTrace();
            }
        } else {
            log.info("空");
        }
    }

}

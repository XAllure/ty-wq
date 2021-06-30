package com.ty.wq.handler;

import com.ty.wq.constant.MsgType;
import com.ty.wq.enums.CodeEnum;
import com.ty.wq.pojo.vo.client.user.UserRespVo;
import com.ty.wq.pojo.vo.netty.Message;
import com.ty.wq.pojo.vo.netty.MsgVo;
import com.ty.wq.service.client.UserService;
import com.ty.wq.utils.ChannelUtils;
import com.ty.wq.utils.MsgUtils;
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
    private UserService userService;

    public void handler(ChannelHandlerContext ctx, MsgVo msgVo) {
        if (msgVo != null) {
            try {
                switch (msgVo.getType()) {
                    case MsgType.HEART_BEAT: {
                        log.info("HEARTBEAT 操作");
                        break;
                    }
                    case MsgType.RECEIVED: {
                        log.info("RECEIVED 操作");
                        break;
                    }
                    // ty-wq-netty-test的scanner测试能不能获取到数据
                    case MsgType.MyInfo: {
                        UserRespVo vo = userService.getById(ChannelUtils.getUserId(ctx.channel()));
                        msgVo.setData(vo);
                        log.info("返回消息：{}", msgVo);
                        MsgUtils.writeJson(ctx.channel(), Message.success(msgVo));
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

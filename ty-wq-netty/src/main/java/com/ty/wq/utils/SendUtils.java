package com.ty.wq.utils;

import com.ty.wq.pojo.vo.netty.Message;
import com.ty.wq.pojo.vo.netty.MsgVo;
import io.netty.channel.Channel;

import java.util.List;

/**
 * @author Administrator
 */
public class SendUtils {

    /**
     * 发送数据
     * @param wechatId 微信id
     * @param type 类型
     * @param data 数据
     */
    public static void send(String wechatId, String type, Object data) {
        List<Channel> channels = ChannelUtils.getChannelsByWechatId(wechatId);
        MsgVo msgVo = new MsgVo();
        msgVo.setType(type);
        msgVo.setData(data);
        if (channels != null) {
            for (Channel ch : channels) {
                MsgUtils.writeJson(ch, Message.success(msgVo));
            }
        }
    }

}

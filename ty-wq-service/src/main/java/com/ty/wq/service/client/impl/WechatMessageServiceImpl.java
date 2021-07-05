package com.ty.wq.service.client.impl;

import com.ty.wq.constant.MsgType;
import com.ty.wq.dao.client.WechatMessageDao;
import com.ty.wq.pojo.po.client.Wechat;
import com.ty.wq.pojo.po.client.WechatFriend;
import com.ty.wq.pojo.vo.client.wechatMessage.WechatMessageReqVo;
import com.ty.wq.pojo.vo.client.wechatMessage.WechatMessageRespVo;
import com.ty.wq.pojo.vo.client.wechatMessage.WechatMessageSearchVo;
import com.ty.wq.pojo.po.client.WechatMessage;
import com.ty.wq.pojo.vo.netty.Message;
import com.ty.wq.pojo.vo.netty.MsgVo;
import com.ty.wq.service.client.WechatFriendService;
import com.ty.wq.service.client.WechatMessageService;
import com.ty.wq.service.base.impl.BaseServiceImpl;
import com.ty.wq.service.client.WechatService;
import com.ty.wq.utils.ChannelUtils;
import com.ty.wq.utils.MsgUtils;
import com.ty.wq.utils.OrikaUtils;
import io.netty.channel.Channel;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author Administrator
 * @version 1.0
 * @program: ty-wq
 * @description:
 * @date 2021-06-21 03:41:10
 */
@Service
public class WechatMessageServiceImpl extends BaseServiceImpl<WechatMessage, WechatMessageDao, WechatMessageSearchVo> implements WechatMessageService {

    @Autowired
    @Lazy
    private WechatFriendService wechatFriendService;

    @Autowired
    private WechatService wechatService;

    @Override
    public void send(WechatMessageReqVo vo) {
        WechatMessage message = OrikaUtils.convert(vo, WechatMessage.class);
        message.setCreateTime(new Timestamp(System.currentTimeMillis()));

        // 赋值公司ID和部门ID
        /*Wechat wechat = wechatService.findByWechatId(vo.getWechatId());
        message.setCompanyId(wechat.getCompanyId());
        message.setDepartmentId(wechat.getDepartmentId());*/

        MsgVo msgVo = new MsgVo();
        WechatMessageRespVo respVo = OrikaUtils.convert(message, WechatMessageRespVo.class);
        msgVo.setData(respVo);
        // roomWxId(群微信id)为空，则是私聊(待完成)
        if (StringUtils.isBlank(vo.getRoomWxId())) {
            msgVo.setType(MsgType.PRIVATE_CHAT);

            // 修改与好友的上次联系时间
            /*WechatFriend wechatFriend = wechatFriendService.getByWechatIdAndFriendId(vo.getWxIdFrom(), vo.getWxIdTo());
            wechatFriend.setContactTime(new Timestamp(System.currentTimeMillis()));
            wechatFriendService.updateById(wechatFriend);*/
        } else {
            // 否则为群聊(待完成)
            msgVo.setType(MsgType.ROOM_CHAT);
        }
        insert(message);
    }
}

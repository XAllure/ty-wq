package com.ty.wq.service.client.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ty.wq.dao.client.WeChatDao;
import com.ty.wq.enums.StatusEnum;
import com.ty.wq.pojo.vo.client.weChat.WeChatReqVo;
import com.ty.wq.pojo.vo.client.weChat.WeChatSearchVo;
import com.ty.wq.pojo.po.client.WeChat;
import com.ty.wq.service.client.WeChatService;
import com.ty.wq.service.base.impl.BaseServiceImpl;
import com.ty.wq.utils.AccessUtils;
import com.ty.wq.utils.ChannelUtils;
import io.netty.channel.Channel;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * @version 1.0
 * @program: ty-wq
 * @description:
 * @date 2021-06-15 07:08:40
 */
@Service
public class WeChatServiceImpl extends BaseServiceImpl<WeChat, WeChatDao, WeChatSearchVo> implements WeChatService {
    @Override
    public WeChat findByWeChatId(String weChatId) {
        QueryWrapper<WeChat> qw = new QueryWrapper<>();
        qw.in("we_chat_id", weChatId);
        return findOne(qw);
    }

    @Override
    public List<WeChat> login(WeChatReqVo weChatReqVo) {
        List<WeChat> weChats = new ArrayList<>();
        for (String weChatId : weChatReqVo.getWeChatIds()) {
            WeChat weChat = findByWeChatId(weChatId);
            if (null != weChat) {
                weChat.setIsLogin(StatusEnum.LOGGED_IN.getCode());
                weChat.setIsOnline(StatusEnum.ONLINE.getCode());
                weChat.setLoginTime(new Timestamp(System.currentTimeMillis()));
                updateById(weChat);
                weChats.add(weChat);
            }
        }
        // 获取用户的channel
        Channel channel = ChannelUtils.userChannel(AccessUtils.userId());
        // 保存微信id与用户channel的对应关系
        ChannelUtils.saveWeChatChannels(channel, weChats);
        // 返回已登录的微信
        return weChats;
    }

    @Override
    public List<WeChat> findByIds(List<Long> ids) {
        return findBatchIds(ids);
    }
}

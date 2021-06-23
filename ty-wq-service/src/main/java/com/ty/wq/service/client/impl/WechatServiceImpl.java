package com.ty.wq.service.client.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ty.wq.dao.client.WechatDao;
import com.ty.wq.enums.StatusEnum;
import com.ty.wq.pojo.vo.client.wechat.WechatReqVo;
import com.ty.wq.pojo.vo.client.wechat.WechatSearchVo;
import com.ty.wq.pojo.po.client.Wechat;
import com.ty.wq.service.client.WechatService;
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
public class WechatServiceImpl extends BaseServiceImpl<Wechat, WechatDao, WechatSearchVo> implements WechatService {
    @Override
    public Wechat findByWechatId(String weChatId) {
        QueryWrapper<Wechat> qw = new QueryWrapper<>();
        qw.in("wechat_id", weChatId);
        return findOne(qw);
    }

    @Override
    public List<Wechat> login(WechatReqVo wechatReqVo) {
        List<Wechat> wechats = new ArrayList<>();
        for (String weChatId : wechatReqVo.getWeChatIds()) {
            Wechat weChat = findByWechatId(weChatId);
            if (null != weChat) {
                weChat.setIsLogin(StatusEnum.LOGGED_IN.getCode());
                weChat.setIsOnline(StatusEnum.ONLINE.getCode());
                weChat.setLoginTime(new Timestamp(System.currentTimeMillis()));
                updateById(weChat);
                wechats.add(weChat);
            }
        }
        // 获取用户的channel
        Channel channel = ChannelUtils.userChannel(AccessUtils.userId());
        // 保存微信id与用户channel的对应关系
        ChannelUtils.saveWeChatChannels(channel, wechats);
        // 返回已登录的微信
        return wechats;
    }

    @Override
    public List<Wechat> findByIds(List<Long> ids) {
        return findBatchIds(ids);
    }
}

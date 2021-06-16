package com.ty.wq.service.client.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ty.wq.dao.client.UserWeChatDao;
import com.ty.wq.pojo.vo.client.userWeChat.UserWeChatSearchVo;
import com.ty.wq.pojo.po.client.UserWeChat;
import com.ty.wq.service.client.UserWeChatService;
import com.ty.wq.service.base.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * @version 1.0
 * @program: ty-wq
 * @description:
 * @date 2021-06-15 10:02:58
 */
@Service
public class UserWeChatServiceImpl extends BaseServiceImpl<UserWeChat, UserWeChatDao, UserWeChatSearchVo> implements UserWeChatService {

    @Override
    public List<Long> getWeChatIds(Long userId) {
        QueryWrapper<UserWeChat> qw = new QueryWrapper<>();
        qw.eq("user_id", userId);
        List<UserWeChat> userWeChats = findList(qw);
        List<Long> ids = new ArrayList<>();
        for (UserWeChat userWeChat : userWeChats) {
            ids.add(userWeChat.getWeChatId());
        }
        return ids;
    }
}

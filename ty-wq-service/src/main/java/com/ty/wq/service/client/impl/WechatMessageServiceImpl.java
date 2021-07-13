package com.ty.wq.service.client.impl;

import com.ty.wq.dao.client.WechatMessageDao;
import com.ty.wq.pojo.vo.client.wechatMessage.WechatMessageSearchVo;
import com.ty.wq.pojo.po.client.WechatMessage;
import com.ty.wq.service.client.WechatFriendService;
import com.ty.wq.service.client.WechatMessageService;
import com.ty.wq.service.base.impl.BaseServiceImpl;
import com.ty.wq.service.client.WechatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

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
}

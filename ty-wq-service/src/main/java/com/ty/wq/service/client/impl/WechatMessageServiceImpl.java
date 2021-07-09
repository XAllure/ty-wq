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

}

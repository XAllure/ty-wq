package com.ty.wq.service.client.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ty.wq.constant.MsgType;
import com.ty.wq.dao.client.WechatFriendDao;
import com.ty.wq.enums.StatusEnum;
import com.ty.wq.enums.WechatEnum;
import com.ty.wq.pojo.po.client.Company;
import com.ty.wq.pojo.po.client.Department;
import com.ty.wq.pojo.po.client.WechatMessage;
import com.ty.wq.pojo.vo.client.wechatFriend.WechatFriendReqVo;
import com.ty.wq.pojo.vo.client.wechatFriend.WechatFriendRespVo;
import com.ty.wq.pojo.vo.client.wechatFriend.WechatFriendSearchVo;
import com.ty.wq.pojo.po.client.WechatFriend;
import com.ty.wq.pojo.vo.client.wechatMessage.WechatMessageRespVo;
import com.ty.wq.pojo.vo.netty.Message;
import com.ty.wq.pojo.vo.netty.MsgVo;
import com.ty.wq.service.client.CompanyService;
import com.ty.wq.service.client.DepartmentService;
import com.ty.wq.service.client.WechatFriendService;
import com.ty.wq.service.base.impl.BaseServiceImpl;
import com.ty.wq.utils.ChannelUtils;
import com.ty.wq.utils.MsgUtils;
import com.ty.wq.utils.OrikaUtils;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author Administrator
 * @version 1.0
 * @program: ty-wq
 * @description:
 * @date 2021-06-21 03:40:57
 */
@Service
public class WechatFriendServiceImpl extends BaseServiceImpl<WechatFriend, WechatFriendDao, WechatFriendSearchVo> implements WechatFriendService {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private DepartmentService departmentService;

    @Override
    public void newFriend(WechatFriendReqVo vo) {
        WechatFriend wechatFriend = OrikaUtils.convert(vo, WechatFriend.class);
        wechatFriend.setStatus(WechatEnum.FRIEND_NEW.getCode());
        insert(wechatFriend);
        WechatFriendRespVo respVo = OrikaUtils.convert(wechatFriend, WechatFriendRespVo.class);
        // 根据好友的微信id获取channel
        List<Channel> channels = ChannelUtils.getChannelsByWechatId(wechatFriend.getFriendId());
        MsgVo msgVo = new MsgVo();
        msgVo.setType(MsgType.NEW_FRIEND);
        msgVo.setData(respVo);
        for (Channel channel : channels) {
            // 通知好友有新的好友申请
            MsgUtils.writeJson(channel, Message.success(msgVo));
        }
    }

    @Override
    public List<WechatFriendRespVo> getNewFriends(String wechatId) {
        QueryWrapper<WechatFriend> qw = new QueryWrapper<>();
        qw.eq("wechat_id", wechatId)
                .eq("status", WechatEnum.FRIEND_NEW.getCode());
        List<WechatFriendRespVo> vos = OrikaUtils.converts(findList(qw), WechatFriendRespVo.class);
        // vos.forEach(this::setCd);
        return vos;
    }

    @Override
    public void handleNewFriend(WechatFriendReqVo vo) {
        WechatFriend wechatFriend = findById(vo.getId());
        OrikaUtils.copy(vo, wechatFriend);
        updateById(wechatFriend);
        // 如果通过好友申请
        if (wechatFriend.getStatus().equals(WechatEnum.FRIEND_NORMAL.getCode())) {
            // 分发消息
            List<Channel> channels = ChannelUtils.getChannelsByWechatId(wechatFriend.getWechatId());
            MsgVo msgVo = new MsgVo();
            msgVo.setType(MsgType.NEW_FRIEND_SUCCESS);
            // 后续操作
            for (Channel channel : channels) {

            }
        }
    }

    @Override
    public List<WechatFriendRespVo> getWechatFriends(String wechatId) {
        QueryWrapper<WechatFriend> qw = new QueryWrapper<>();
        qw.eq("wechat_id", wechatId).eq("status", WechatEnum.FRIEND_NORMAL.getCode());
        List<WechatFriendRespVo> vos = OrikaUtils.converts(findList(qw), WechatFriendRespVo.class);
        vos.forEach(this::setCd);
        return vos;
    }



    /**
     * 为 WechatFriendRespVo 注入公司名称和部门名称
     * @param vo
     */
    private void setCd(WechatFriendRespVo vo){
        if (Objects.nonNull(vo.getCompanyId())) {
            Company company = companyService.findById(vo.getCompanyId());
            vo.setCompanyName(company.getName());
        }
        if (Objects.nonNull(vo.getDepartmentId())) {
            Department department = departmentService.findById(vo.getDepartmentId());
            vo.setDepartmentName(department.getName());
        }
    }

}

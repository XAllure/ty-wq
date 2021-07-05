package com.ty.wq.service.client.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ty.wq.constant.MsgType;
import com.ty.wq.dao.client.WechatFriendDao;
import com.ty.wq.enums.CodeEnum;
import com.ty.wq.enums.WechatEnum;
import com.ty.wq.exception.WqException;
import com.ty.wq.pojo.po.client.*;
import com.ty.wq.pojo.vo.client.wechatFriend.WechatFriendReqVo;
import com.ty.wq.pojo.vo.client.wechatFriend.WechatFriendRespVo;
import com.ty.wq.pojo.vo.client.wechatFriend.WechatFriendSearchVo;
import com.ty.wq.pojo.vo.client.wechatMessage.WechatMessageRespVo;
import com.ty.wq.pojo.vo.netty.Message;
import com.ty.wq.pojo.vo.netty.MsgVo;
import com.ty.wq.service.client.*;
import com.ty.wq.service.base.impl.BaseServiceImpl;
import com.ty.wq.utils.ChannelUtils;
import com.ty.wq.utils.MsgUtils;
import com.ty.wq.utils.OrikaUtils;
import io.netty.channel.Channel;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
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

    @Autowired
    private WechatService wechatService;

    @Autowired
    private WechatMessageService wechatMessageService;

    /**
     * 好友验证申请
     * @param vo
     */
    @Override
    public void newFriend(WechatFriendReqVo vo) {
        if (vo.getScene().equals(WechatEnum.SCENE_CARD.getCode())) {
            if (StringUtils.isBlank(vo.getV1())) {
                throw new WqException(CodeEnum.ERROR.getCode(), "用户v1不能为空");
            }
        }
        WechatFriend wechatFriend = OrikaUtils.convert(vo, WechatFriend.class);
        wechatFriend.setStatus(WechatEnum.FRIEND_NEW.getCode());
        wechatFriend.setTop(WechatEnum.FRIEND_NOT_TOP.getCode());
        wechatFriend.setDisturb(WechatEnum.FRIEND_NOT_DISTURB.getCode());
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

    /**
     * 获取好友申请列表
     * @param wechatId
     * @return
     */
    @Override
    public List<WechatFriendRespVo> getNewFriends(String wechatId) {
        QueryWrapper<WechatFriend> qw = new QueryWrapper<>();
        qw.eq("wechat_id", wechatId)
                .eq("status", WechatEnum.FRIEND_NEW.getCode());
        List<WechatFriendRespVo> vos = OrikaUtils.converts(findList(qw), WechatFriendRespVo.class);
        vos.forEach(this::setCd);
        return vos;
    }

    /**
     * 处理好友状态
     * @param vo
     */
    @Override
    public void handleFriend(WechatFriendReqVo vo) {
        WechatFriend wechatFriend = getByWechatIdAndFriendId(vo.getWechatId(), vo.getFriendId());
        wechatFriend.setStatus(vo.getStatus());
        updateById(wechatFriend);
        // 如果通过好友申请(未完成)
        if (wechatFriend.getStatus().equals(WechatEnum.FRIEND_NORMAL.getCode())) {
            // 获取自己的channel
            List<Channel> wcChannels = ChannelUtils.getChannelsByWechatId(vo.getWechatId());
            MsgVo msgVo = new MsgVo();
            msgVo.setType(MsgType.PRIVATE_CHAT);
            // 定义消息
            WechatMessage wcMsg = new WechatMessage();
            wcMsg.setWechatId(vo.getWechatId());
            wcMsg.setMsgType(WechatEnum.MSG_TYPE_TEXT.getCode());
            wcMsg.setWxIdFrom(vo.getFriendId());
            wcMsg.setWxIdTo(vo.getWechatId());
            wcMsg.setContent(StringUtils.isNotBlank(wechatFriend.getRemark()) ?
                    wechatFriend.getRemark() : "我们已经成为好友了，可以开始愉快的聊天了");
            wcMsg.setTimestamp(System.currentTimeMillis());
            wcMsg.setCompanyId(wechatFriend.getCompanyId());
            wcMsg.setDepartmentId(wechatFriend.getDepartmentId());
            wcMsg.setIsSend(WechatEnum.IS_SEND.getCode());
            wcMsg.setIsPc(WechatEnum.NOT_PC.getCode());
            wcMsg.setCreateTime(new Timestamp(System.currentTimeMillis()));
            WechatMessageRespVo wcMessageRespVo = OrikaUtils.convert(wcMsg, WechatMessageRespVo.class);
            msgVo.setData(wcMessageRespVo);
            // 将好友验证的备注发送给自己(未完成)
            for (Channel channel : wcChannels) {
                MsgUtils.writeJson(channel, Message.success(msgVo));
            }

            // 获取好友的channel
            List<Channel> channels = ChannelUtils.getChannelsByWechatId(vo.getFriendId());
            MsgVo msgVo1 = new MsgVo();
            msgVo1.setType(MsgType.NEW_FRIEND_SUCCESS);
            // 定义消息
            WechatMessage message = new WechatMessage();
            message.setWechatId(vo.getWechatId());
            message.setMsgType(WechatEnum.MSG_TYPE_TEXT.getCode());
            message.setWxIdFrom(vo.getWechatId());
            message.setWxIdTo(vo.getFriendId());
            message.setContent("我通过了你的好友申请，可以开始愉快的聊天了");
            message.setTimestamp(System.currentTimeMillis());
            Wechat wechat = wechatService.findByWechatId(vo.getWechatId());
            message.setCompanyId(wechat.getCompanyId());
            message.setDepartmentId(wechat.getDepartmentId());
            message.setIsSend(WechatEnum.IS_SEND.getCode());
            message.setIsPc(WechatEnum.NOT_PC.getCode());
            message.setCreateTime(new Timestamp(System.currentTimeMillis()));
            WechatMessageRespVo messageRespVo = OrikaUtils.convert(message, WechatMessageRespVo.class);
            msgVo1.setData(messageRespVo);
            // 分发消息(未完成)
            for (Channel channel : channels) {

                // 通知好友申请通过
                MsgUtils.writeJson(channel, Message.success(msgVo1));
            }
            // 保存消息
            wechatMessageService.insert(wcMsg);
            wechatMessageService.insert(message);
        }
    }

    /**
     * 获取好友列表
     * @param wechatId
     * @return
     */
    @Override
    public List<WechatFriendRespVo> getWechatFriends(String wechatId) {
        QueryWrapper<WechatFriend> qw = new QueryWrapper<>();
        qw.eq("wechat_id", wechatId).eq("status", WechatEnum.FRIEND_NORMAL.getCode());
        List<WechatFriendRespVo> vos = OrikaUtils.converts(findList(qw), WechatFriendRespVo.class);
        vos.forEach(this::setCd);
        return vos;
    }

    /**
     * 根据微信id和好友id获取好友信息
     * @param wechatId
     * @param friendId
     * @return
     */
    @Override
    public WechatFriend getByWechatIdAndFriendId(String wechatId, String friendId) {
        QueryWrapper<WechatFriend> qw = new QueryWrapper<>();
        qw.eq("wechat_id", wechatId).eq("friend_id", wechatId);
        return findOne(qw);
    }

    /**
     * 根据微信ID、好友微信ID获取微信好友信息
     * @param vo
     * @return
     */
    @Override
    public WechatFriendRespVo getFriendInfo(WechatFriendReqVo vo) {
        WechatFriend wechatFriend = getByWechatIdAndFriendId(vo.getWechatId(), vo.getFriendId());
        WechatFriendRespVo respVo = OrikaUtils.convert(wechatFriend, WechatFriendRespVo.class);
        setCd(respVo);
        return respVo;
    }

    /**
     * 根据id获取微信好友信息
     * @param id
     * @return
     */
    @Override
    public WechatFriendRespVo getFriendInfo(Long id) {
        WechatFriend wechatFriend = findById(id);
        WechatFriendRespVo respVo = OrikaUtils.convert(wechatFriend, WechatFriendRespVo.class);
        setCd(respVo);
        return respVo;
    }

    /**
     * 修改好友部分信息
     * @param vo
     */
    @Override
    public void updateFriendInfo(WechatFriendReqVo vo) {
        WechatFriend wf = getByWechatIdAndFriendId(vo.getWechatId(), vo.getFriendId());
        wf.setRemark(vo.getRemark());
        wf.setRemarkName(vo.getRemarkName());
        wf.setRemarkPhone(vo.getRemarkPhone());
        wf.setRemarkTags(vo.getRemarkTags());
        updateById(wf);
    }

    /**
     * 是否置顶
     * @param vo
     */
    @Override
    public void toTop(WechatFriendReqVo vo) {
        WechatFriend wechatFriend = getByWechatIdAndFriendId(vo.getWechatId(), vo.getFriendId());
        wechatFriend.setTop(vo.getTop());
        updateById(wechatFriend);
    }

    /**
     * 是否免打扰
     * @param vo
     */
    @Override
    public void toDisturb(WechatFriendReqVo vo) {
        WechatFriend wechatFriend = getByWechatIdAndFriendId(vo.getWechatId(), vo.getFriendId());
        wechatFriend.setTop(vo.getDisturb());
        updateById(wechatFriend);
    }


    /**
     * 为 WechatFriendRespVo 注入公司名称和部门名称
     * @param respVo
     */
    private void setCd(WechatFriendRespVo respVo){
        if (Objects.nonNull(respVo.getCompanyId())) {
            Company company = companyService.findById(respVo.getCompanyId());
            respVo.setCompanyName(company.getName());
        }
        if (Objects.nonNull(respVo.getDepartmentId())) {
            Department department = departmentService.findById(respVo.getDepartmentId());
            respVo.setDepartmentName(department.getName());
        }
    }

}

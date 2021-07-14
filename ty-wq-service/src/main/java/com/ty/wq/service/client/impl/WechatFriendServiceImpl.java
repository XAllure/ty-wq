package com.ty.wq.service.client.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ty.wq.constant.ApiType;
import com.ty.wq.constant.OptionKey;
import com.ty.wq.dao.client.WechatFriendDao;
import com.ty.wq.enums.CodeEnum;
import com.ty.wq.enums.WechatEnum;
import com.ty.wq.pojo.po.client.*;
import com.ty.wq.pojo.vo.Result;
import com.ty.wq.pojo.vo.client.wechatFriend.WechatFriendReqVo;
import com.ty.wq.pojo.vo.client.wechatFriend.WechatFriendRespVo;
import com.ty.wq.pojo.vo.client.wechatFriend.WechatFriendSearchVo;
import com.ty.wq.pojo.vo.client.wechatMessage.SendMsg;
import com.ty.wq.pojo.vo.netty.Option;
import com.ty.wq.service.client.*;
import com.ty.wq.service.base.impl.BaseServiceImpl;
import com.ty.wq.utils.OrikaUtils;
import com.ty.wq.utils.RouteUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Administrator
 * @version 1.0
 * @program: ty-wq
 * @description:
 * @date 2021-06-21 03:40:57
 */
@Service
public class WechatFriendServiceImpl extends BaseServiceImpl<WechatFriend, WechatFriendDao, WechatFriendSearchVo> implements WechatFriendService {

    /**
     * 获取好友列表
     * @param wechatId
     * @return
     */
    @Override
    public List<WechatFriendRespVo> getWechatFriends(String wechatId) {
        QueryWrapper<WechatFriend> qw = new QueryWrapper<>();
        qw.eq("wechat_id", wechatId).eq("status", WechatEnum.FRIEND_NORMAL.getCode());
        return OrikaUtils.converts(findList(qw), WechatFriendRespVo.class);
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
        qw.eq("wechat_id", wechatId).eq("friend_id", friendId);
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
        return OrikaUtils.convert(wechatFriend, WechatFriendRespVo.class);
    }

    /**
     * 添加好友
     * @param vo
     * @return res
     */
    @Override
    public Result addFriend(WechatFriendReqVo vo) {
        SendMsg sendMsg = new SendMsg();
        sendMsg.setApi(ApiType.ADD_FRIEND);
        sendMsg.setSendId(vo.getWechatId());
        sendMsg.setOption(Option.option()
                .add(OptionKey.WXID, vo.getFriendId())
                .add(OptionKey.REMARK, vo.getRemark())
                .add(OptionKey.SCENE, vo.getScene())
                .add(OptionKey.ROOM_WXID, "")
                .getOption());
        // 通知netty服务端
        Result res = RouteUtils.send(sendMsg);
        if (res.getCode().equals(CodeEnum.SUCCESS.getCode())) {
            // 如果之前添加过
            WechatFriend wf = getByWechatIdAndFriendId(vo.getWechatId(), vo.getFriendId());
            if (wf != null) {
                if (!wf.getStatus().equals(WechatEnum.FRIEND_NORMAL.getCode())) {
                    wf.setStatus(WechatEnum.FRIEND_NEW.getCode());
                    wf.setScene(vo.getScene());
                    wf.setDeleted(0);
                    updateById(wf);
                }
            } else {
                // 没有添加过
                WechatFriend wechatFriend = OrikaUtils.convert(vo, WechatFriend.class);
                wechatFriend.setStatus(WechatEnum.FRIEND_NEW.getCode());
                insert(wechatFriend);
            }
        }
        return res;
    }

    /**
     * 添加通过任意手机号/微信号/QQ号查询的联系人
     * @param vo
     * @return
     */
    @Override
    public Result addSearchContact(WechatFriendReqVo vo) {
        SendMsg sendMsg = new SendMsg();
        sendMsg.setApi(ApiType.ADD_FRIEND);
        sendMsg.setSendId(vo.getWechatId());
        sendMsg.setOption(Option.option()
                .add(OptionKey.V1, vo.getV1())
                .add(OptionKey.V2, vo.getV2())
                .add(OptionKey.REMARK, vo.getRemark())
                .add(OptionKey.SCENE, vo.getScene())
                .getOption());
        // 通知netty服务端
        Result res = RouteUtils.send(sendMsg);
        if (res.getCode().equals(CodeEnum.SUCCESS.getCode())) {
            // 如果之前添加过
            WechatFriend wf = getByWechatIdAndFriendId(vo.getWechatId(), vo.getFriendId());
            if (wf != null) {
                if (!wf.getStatus().equals(WechatEnum.FRIEND_NORMAL.getCode())) {
                    wf.setStatus(WechatEnum.FRIEND_NEW.getCode());
                    wf.setScene(vo.getScene());
                    wf.setDeleted(0);
                    updateById(wf);
                }
            } else {
                // 没有添加过
                WechatFriend wechatFriend = OrikaUtils.convert(vo, WechatFriend.class);
                wechatFriend.setStatus(WechatEnum.FRIEND_NEW.getCode());
                insert(wechatFriend);
            }
        }
        return null;
    }

    /**
     * 修改好友备注
     * @param vo
     * @return
     */
    @Override
    public Result updateRemark(WechatFriendReqVo vo) {
        SendMsg sendMsg = new SendMsg();
        sendMsg.setApi(ApiType.UPDATE_REMARK);
        sendMsg.setSendId(vo.getWechatId());
        sendMsg.setOption(Option.option()
                .add(OptionKey.WXID, vo.getFriendId())
                .add(OptionKey.REMARK, vo.getRemarkName())
                .getOption());
        // 通知netty服务端
        Result res = RouteUtils.send(sendMsg);
        if (res.getCode().equals(CodeEnum.SUCCESS.getCode())) {
            WechatFriend wechatFriend = getByWechatIdAndFriendId(vo.getWechatId(), vo.getFriendId());
            wechatFriend.setRemarkName(vo.getRemarkName());
            updateById(wechatFriend);
        }
        return res;
    }

    /**
     * 修改好友部分信息
     * @param vo
     */
    @Override
    public void updateFriendInfo(WechatFriendReqVo vo) {
        WechatFriend wf = getByWechatIdAndFriendId(vo.getWechatId(), vo.getFriendId());
        wf.setRemarkName(vo.getRemarkName());
        wf.setRemarkPhone(vo.getRemarkPhone());
        wf.setRemarkTags(vo.getRemarkTags());
        updateById(wf);
    }

    /**
     * 是否置顶
     * @param vo
     * @return
     */
    @Override
    public Result toTop(WechatFriendReqVo vo) {
        SendMsg sMsg = new SendMsg();
        sMsg.setApi(ApiType.CHAT_SESSION_TOP);
        sMsg.setSendId(vo.getWechatId());
        sMsg.setOption(Option.option()
                .add(OptionKey.WXID, vo.getFriendId())
                .add(OptionKey.CODE, vo.getTop())
                .getOption());
        Result res = RouteUtils.send(sMsg);
        if (res.getCode().equals(CodeEnum.SUCCESS.getCode())) {
            WechatFriend wechatFriend = getByWechatIdAndFriendId(vo.getWechatId(), vo.getFriendId());
            wechatFriend.setTop(vo.getTop());
            updateById(wechatFriend);
        }
        return res;
    }

    /**
     * 是否免打扰
     * @param vo
     */
    @Override
    public Result toDisturb(WechatFriendReqVo vo) {
        SendMsg sMsg = new SendMsg();
        sMsg.setApi(ApiType.MOD_RECV_NOTIFY);
        sMsg.setSendId(vo.getWechatId());
        sMsg.setOption(Option.option()
                .add(OptionKey.WXID, vo.getFriendId())
                .add(OptionKey.CODE, vo.getDisturb())
                .getOption());
        Result res = RouteUtils.send(sMsg);
        if (res.getCode().equals(CodeEnum.SUCCESS.getCode())) {
            WechatFriend wechatFriend = getByWechatIdAndFriendId(vo.getWechatId(), vo.getFriendId());
            wechatFriend.setDisturb(vo.getDisturb());
            updateById(wechatFriend);
        }
        return res;
    }

}

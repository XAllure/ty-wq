package com.ty.wq.service.client.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ty.wq.dao.client.WechatFriendDao;
import com.ty.wq.enums.WechatEnum;
import com.ty.wq.pojo.po.client.*;
import com.ty.wq.pojo.vo.client.wechatFriend.WechatFriendReqVo;
import com.ty.wq.pojo.vo.client.wechatFriend.WechatFriendRespVo;
import com.ty.wq.pojo.vo.client.wechatFriend.WechatFriendSearchVo;
import com.ty.wq.service.client.*;
import com.ty.wq.service.base.impl.BaseServiceImpl;
import com.ty.wq.utils.OrikaUtils;
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
    public void addFriend(WechatFriendReqVo vo) {
        // 如果之前添加过
        WechatFriend friend = getByWechatIdAndFriendId(vo.getWechatId(), vo.getFriendId());
        if (friend != null) {
            if (!friend.getStatus().equals(WechatEnum.FRIEND_NORMAL.getCode())) {
                friend.setStatus(WechatEnum.FRIEND_NEW.getCode());
                friend.setScene(vo.getScene());
                friend.setDeleted(0);
                updateById(friend);
            }
        } else {
            // 没有添加过
            WechatFriend wechatFriend = OrikaUtils.convert(vo, WechatFriend.class);
            wechatFriend.setStatus(WechatEnum.FRIEND_NEW.getCode());
            insert(wechatFriend);
        }
    }

    /**
     * 修改好友备注
     * @param vo
     */
    @Override
    public void updateRemark(WechatFriendReqVo vo) {
        WechatFriend wechatFriend = getByWechatIdAndFriendId(vo.getWechatId(), vo.getFriendId());
        wechatFriend.setRemarkName(vo.getRemarkName());
        updateById(wechatFriend);
    }

    /**
     * 修改好友部分信息
     * @param vo
     */
    @Override
    public void updateFriendInfo(WechatFriendReqVo vo) {
        WechatFriend wf = getByWechatIdAndFriendId(vo.getWechatId(), vo.getFriendId());
        wf.setRemark(vo.getRemark());
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
        wechatFriend.setDisturb(vo.getDisturb());
        updateById(wechatFriend);
    }

}

package com.ty.wq.service.client.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ty.wq.constant.MsgType;
import com.ty.wq.dao.client.WechatFriendDao;
import com.ty.wq.enums.CodeEnum;
import com.ty.wq.enums.WechatEnum;
import com.ty.wq.exception.WqException;
import com.ty.wq.pojo.po.client.*;
import com.ty.wq.pojo.vo.Result;
import com.ty.wq.pojo.vo.client.wechatFriend.WechatFriendReqVo;
import com.ty.wq.pojo.vo.client.wechatFriend.WechatFriendRespVo;
import com.ty.wq.pojo.vo.client.wechatFriend.WechatFriendSearchVo;
import com.ty.wq.pojo.vo.client.wechatMessage.SendMsg;
import com.ty.wq.pojo.vo.client.wechatMessage.WechatMessageRespVo;
import com.ty.wq.pojo.vo.netty.Message;
import com.ty.wq.pojo.vo.netty.MsgVo;
import com.ty.wq.service.client.*;
import com.ty.wq.service.base.impl.BaseServiceImpl;
import com.ty.wq.utils.ChannelUtils;
import com.ty.wq.utils.MsgUtils;
import com.ty.wq.utils.OrikaUtils;
import com.ty.wq.utils.RouteUtils;
import io.netty.channel.Channel;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
     * 添加好友
     * @param vo
     */
    @Override
    public Result addFriend(WechatFriendReqVo vo) {
        WechatFriend wechatFriend = OrikaUtils.convert(vo, WechatFriend.class);
        SendMsg sendMsg = new SendMsg();
        sendMsg.setApi(MsgType.ADD_FRIEND);
        sendMsg.setSendId(vo.getWechatId());
        Map<String, Object> map = new HashMap<>();
        map.put("wxid", vo.getFriendId());
        map.put("remark", vo.getRemark());
        map.put("scene", vo.getScene());
        map.put("roomWxid", "");
        sendMsg.setOption(map);
        // 通知netty服务端
        Result res = RouteUtils.result(sendMsg, "/wechat/friend/addFried");
        if (res.getCode().equals(CodeEnum.SUCCESS.getCode())) {
            wechatFriend.setStatus(WechatEnum.FRIEND_NEW.getCode());
            insert(wechatFriend);
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

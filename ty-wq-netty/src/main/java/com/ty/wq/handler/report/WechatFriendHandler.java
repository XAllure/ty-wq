package com.ty.wq.handler.report;
import java.sql.Timestamp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ty.wq.enums.WechatEnum;
import com.ty.wq.pojo.po.client.Wechat;
import com.ty.wq.pojo.po.client.WechatFriend;
import com.ty.wq.pojo.vo.client.wechatFriend.WechatFriendRespVo;
import com.ty.wq.pojo.vo.client.wechatMessage.ReceiveMsg;
import com.ty.wq.service.client.WechatFriendService;
import com.ty.wq.service.client.WechatService;
import com.ty.wq.utils.OrikaUtils;
import com.ty.wq.utils.SendUtils;
import com.ty.wq.vo.WechatFriendVo;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 上报普通好友列表
 * @author Administrator
 */
@Service
@Slf4j
public class WechatFriendHandler {

    @Autowired
    private WechatService wechatService;

    @Autowired
    private WechatFriendService wechatFriendService;

    @Async
    public void friendListHandler(Channel channel, ReceiveMsg rMsg) {
        JSONObject data = JSON.parseObject(String.valueOf(rMsg.getData()));
        // 获取好友列表
        JSONArray friendList = data.getJSONArray("friendList");
        // 转换
        List<WechatFriendVo> vos = OrikaUtils.converts(friendList, WechatFriendVo.class);
        List<WechatFriend> friends = new ArrayList<>();
        vos.forEach(vo -> {
            // 查询是否已有该好友
            WechatFriend friend = wechatFriendService.getByWechatIdAndFriendId(rMsg.getCwxid(), vo.getWxid());
            // 查询微信信息
            Wechat wechat = wechatService.findByWechatId(rMsg.getCwxid());
            // 有该好友
            if (friend != null) {
                friend.setFriendWeChatNo(vo.getAlias());
                friend.setFriendWeChatNick(vo.getNick());
                friend.setHeadPic(vo.getHeadPic());
                friend.setGender(vo.getSex());
                friend.setCountry(vo.getCountry());
                friend.setProvince(vo.getProvince());
                friend.setCity(vo.getCity());
                friend.setCompanyId(wechat.getCompanyId());
                friend.setDepartmentId(wechat.getDepartmentId());
                friend.setRemarkName(vo.getRemark());
                wechatFriendService.updateById(friend);
            } else {
                // 没有该好友
                friend = new WechatFriend();
                friend.setWechatId(rMsg.getCwxid());
                friend.setFriendId(vo.getWxid());
                friend.setFriendWeChatNo(vo.getAlias());
                friend.setFriendWeChatNick(vo.getNick());
                friend.setHeadPic(vo.getHeadPic());
                friend.setGender(vo.getSex());
                friend.setCountry(vo.getCountry());
                friend.setProvince(vo.getProvince());
                friend.setCity(vo.getCity());
                friend.setCompanyId(wechat.getCompanyId());
                friend.setDepartmentId(wechat.getDepartmentId());
                friend.setRemarkName(vo.getRemark());
                friend.setStatus(WechatEnum.FRIEND_NORMAL.getCode());
                friend.setTop(WechatEnum.FRIEND_NOT_TOP.getCode());
                friend.setDisturb(WechatEnum.FRIEND_NOT_DISTURB.getCode());
                wechatFriendService.insert(friend);
            }
            friends.add(friend);
        });
        List<WechatFriendRespVo> respVos = OrikaUtils.converts(friends, WechatFriendRespVo.class);
        SendUtils.send(rMsg.getCwxid(), rMsg.getAction(), respVos);
    }
}

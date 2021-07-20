package com.ty.wq.handler.report;

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
import com.ty.wq.pojo.vo.netty.report.WechatFriendAddVo;
import com.ty.wq.pojo.vo.netty.report.WechatFriendVo;
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

    /**
     * 上报普通好友列表
     * @param rMsg
     */
    @Async
    public void friendListHandler(ReceiveMsg rMsg) {
        JSONObject data = JSON.parseObject(String.valueOf(rMsg.getData()));
        // 获取好友列表
        JSONArray friendList = data.getJSONArray("friendList");
        // 转换
        List<WechatFriendVo> vos = OrikaUtils.converts(friendList, WechatFriendVo.class);
        // List<WechatFriend> friends = new ArrayList<>();
        // 暂时一个个循环判断
        vos.forEach(vo -> {
            // 查询是否已有该好友
            WechatFriend friend = wechatFriendService.getByWechatIdAndFriendId(rMsg.getCwxid(), vo.getWxid());
            // 有该好友 更新
            if (friend != null) {
                setFriend(rMsg.getCwxid(), vo, friend);
                wechatFriendService.updateById(friend);
            } else {
                // 无该好友
                friend = new WechatFriend();
                friend.setWechatId(rMsg.getCwxid());
                friend.setFriendId(vo.getWxid());
                friend.setWechatId(rMsg.getCwxid());
                friend.setStatus(WechatEnum.FRIEND_NORMAL.getCode());
                friend.setFriendId(vo.getWxid());
                setFriend(rMsg.getCwxid(), vo, friend);
                wechatFriendService.insert(friend);
            }
            // friends.add(friend);
        });
        /*List<WechatFriendRespVo> respVos = OrikaUtils.converts(friends, WechatFriendRespVo.class);
        SendUtils.send(rMsg.getCwxid(), rMsg.getAction(), respVos);*/
    }

    /**
     * 上报单个普通好友信息
     * @param rMsg
     */
    @Async
    public void friendInfoHandler(ReceiveMsg rMsg) {
        JSONObject data = JSON.parseObject(String.valueOf(rMsg.getData()));
        WechatFriendVo vo = OrikaUtils.convert(data, WechatFriendVo.class);
        // 查询是否已有该好友
        WechatFriend friend = wechatFriendService.getByWechatIdAndFriendId(rMsg.getCwxid(), vo.getWxid());
        if (friend != null) {
            setFriend(rMsg.getCwxid(), vo, friend);
            wechatFriendService.updateById(friend);
            /*WechatFriendRespVo respVo = OrikaUtils.convert(friend, WechatFriendRespVo.class);
            SendUtils.send(rMsg.getCwxid(), rMsg.getAction(), respVo);*/
        }
    }

    /**
     * 上报任意普通微信反查详细信息
     * @param rMsg
     */
    @Async
    public void updateContactHandler(ReceiveMsg rMsg) {
        JSONObject data = JSON.parseObject(String.valueOf(rMsg.getData()));
        WechatFriendVo vo = OrikaUtils.convert(data, WechatFriendVo.class);
        // 查询是否已有该好友
        WechatFriend friend = wechatFriendService.getByWechatIdAndFriendId(rMsg.getCwxid(), vo.getWxid());
        if (friend != null) {
            // 赋值
            setUpdateContact(rMsg.getCwxid(), vo, friend);
            wechatFriendService.updateById(friend);
        } else {
            friend = new WechatFriend();
            setUpdateContact(rMsg.getCwxid(), vo, friend);
        }
        WechatFriendRespVo respVo = OrikaUtils.convert(friend, WechatFriendRespVo.class);
        SendUtils.send(rMsg.getCwxid(), rMsg.getAction(), respVo);
    }
    private void setUpdateContact(String wechatId, WechatFriendVo vo, WechatFriend friend) {
        setFriend(wechatId, vo, friend);
        friend.setSignature(vo.getSignature());
        friend.setSnsPic(vo.getSnspic());
        friend.setScene(vo.getScene());
        friend.setV1(vo.getV1());
        friend.setV2(vo.getV2());
    }

    /**
     * 上报联系人新增通知
     * @param rMsg
     */
    @Async
    public void addFriendHandler(ReceiveMsg rMsg) {
        JSONObject data = JSON.parseObject(String.valueOf(rMsg.getData()));
        WechatFriendVo vo = OrikaUtils.convert(data, WechatFriendVo.class);
        WechatFriend friend = wechatFriendService.getByWechatIdAndFriendId(rMsg.getCwxid(), vo.getWxid());
        if (friend != null) {
            friend.setStatus(WechatEnum.FRIEND_NEW.getCode());
            setFriend(rMsg.getCwxid(), vo, friend);
            wechatFriendService.updateById(friend);
        } else {
            friend = new WechatFriend();
            friend.setWechatId(rMsg.getCwxid());
            friend.setFriendId(vo.getWxid());
            friend.setStatus(WechatEnum.FRIEND_NORMAL.getCode());
            setFriend(rMsg.getCwxid(), vo, friend);
            wechatFriendService.insert(friend);
        }
        WechatFriendRespVo respVo = OrikaUtils.convert(friend, WechatFriendRespVo.class);
        SendUtils.send(rMsg.getCwxid(), rMsg.getAction(), respVo);
    }

    /**
     * 上报联系人删除通知
     * @param rMsg
     */
    @Async
    public void delFriendHandler(ReceiveMsg rMsg) {
        JSONObject data = JSON.parseObject(String.valueOf(rMsg.getData()));
        WechatFriendVo vo = OrikaUtils.convert(data, WechatFriendVo.class);
        WechatFriend friend = wechatFriendService.getByWechatIdAndFriendId(rMsg.getCwxid(), vo.getWxid());
        if (friend != null) {
            friend.setStatus(WechatEnum.FRIEND_DELETED.getCode());
            wechatFriendService.updateById(friend);
            WechatFriendRespVo respVo = OrikaUtils.convert(friend, WechatFriendRespVo.class);
            SendUtils.send(rMsg.getCwxid(), rMsg.getAction(), respVo);
        }
    }

    /**
     * 上报新的加好友请求
     * @param rMsg
     */
    @Async
    public void friendAddRequestHandler(ReceiveMsg rMsg) {
        JSONObject data = JSON.parseObject(String.valueOf(rMsg.getData()));
        WechatFriendAddVo addVo = data.toJavaObject(WechatFriendAddVo.class);
        // 后续再操作
        /*WechatFriend friend = new WechatFriend();
        friend.setWechatId(rMsg.getCwxid());
        friend.setFriendId(addVo.getWxidFrom());
        friend.setStatus(WechatEnum.FRIEND_NEW.getCode());
        friend.setScene();
        friend.setV1();
        friend.setV2();
        wechatFriendService.insert(friend);
        WechatFriendRespVo respVo = OrikaUtils.convert(friend, WechatFriendRespVo.class);
        SendUtils.send(rMsg.getCwxid(), rMsg.getAction(), respVo);*/
        SendUtils.send(rMsg.getCwxid(), rMsg.getAction(), addVo);
    }

    /**
     * 上报加好友指令返回状态
     * @param rMsg
     */
    @Async
    public void addFriendMessageHandler(ReceiveMsg rMsg) {
        JSONObject data = JSON.parseObject(String.valueOf(rMsg.getData()));
        SendUtils.send(rMsg.getCwxid(), rMsg.getAction(), data);
    }

    /**
     * 上报通过手机号/微信号/QQ号查询任意微信号信息
     * @param rMsg
     */
    @Async
    public void searchContactHandler(ReceiveMsg rMsg) {
        JSONObject data = JSON.parseObject(String.valueOf(rMsg.getData()));
        WechatFriendVo vo = OrikaUtils.convert(data, WechatFriendVo.class);
        SendUtils.send(rMsg.getCwxid(), rMsg.getAction(), vo);
    }

    /**
     * 赋值
     * @param wechatId
     * @param vo
     * @param friend
     */
    private void setFriend(String wechatId, WechatFriendVo vo, WechatFriend friend) {
        // 查询微信信息
        Wechat wechat = wechatService.findByWechatId(wechatId);
        // 赋值
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
    }
}

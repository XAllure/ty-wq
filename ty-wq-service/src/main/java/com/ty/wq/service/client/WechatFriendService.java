package com.ty.wq.service.client;

import com.ty.wq.dao.client.WechatFriendDao;
import com.ty.wq.pojo.po.client.WechatFriend;
import com.ty.wq.pojo.vo.client.wechatFriend.WechatFriendReqVo;
import com.ty.wq.pojo.vo.client.wechatFriend.WechatFriendRespVo;
import com.ty.wq.service.base.BaseService;
import com.ty.wq.pojo.vo.client.wechatFriend.WechatFriendSearchVo;
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
public interface WechatFriendService extends BaseService<WechatFriend, WechatFriendDao, WechatFriendSearchVo> {

    /**
     * 添加好友申请
     * @param vo
     */
    void newFriend(WechatFriendReqVo vo);

    /**
     * 获取好友申请列表
     * @param wechatId
     * @return
     */
    List<WechatFriendRespVo> getNewFriends(String wechatId);

    /**
     * 处理好友申请
     * @param vo
     */
    void handleNewFriend(WechatFriendReqVo vo);

    /**
     * 根据微信id获取好友列表
     * @param wechatId
     * @return
     */
    List<WechatFriendRespVo> getWechatFriends(String wechatId);

    /**
     * 获取微信好友信息
     * @param vo
     * @return
     */
    WechatFriendRespVo getFriendInfo(WechatFriendReqVo vo);

    /**
     * 根据id获取微信好友信息
     * @param id
     * @return
     */
    WechatFriendRespVo getFriendInfo(Long id);

    /**
     * 修改好友信息
     * @param vo
     */
    void updateFriendInfo(WechatFriendReqVo vo);

}

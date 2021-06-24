package com.ty.wq.service.client;

import com.ty.wq.dao.client.WechatFriendDao;
import com.ty.wq.pojo.po.client.WechatFriend;
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
     * 根据微信id获取好友列表
     * @param wechatId
     * @return
     */
    List<WechatFriendRespVo> getFriends(String wechatId);

}

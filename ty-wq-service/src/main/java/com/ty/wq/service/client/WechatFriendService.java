package com.ty.wq.service.client;

import com.ty.wq.dao.client.WechatFriendDao;
import com.ty.wq.pojo.po.client.WechatFriend;
import com.ty.wq.pojo.vo.Result;
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
     * 根据微信id获取好友列表
     * @param wechatId
     * @return
     */
    List<WechatFriendRespVo> getWechatFriends(String wechatId);

    /**
     * 根据微信id和好友id获取好友信息
     * @param wechatId
     * @param friendId
     * @return
     */
    WechatFriend getByWechatIdAndFriendId(String wechatId, String friendId);

    /**
     * 获取微信好友信息
     * @param vo
     * @return
     */
    WechatFriendRespVo getFriendInfo(WechatFriendReqVo vo);

    /**
     * 添加好友
     * @param vo
     * @return
     */
    Result addFriend(WechatFriendReqVo vo);






    /**
     * 获取好友申请列表
     * @param wechatId
     * @return
     */
    List<WechatFriendRespVo> getNewFriends(String wechatId);


    /**
     * 修改好友信息
     * @param vo
     */
    void updateFriendInfo(WechatFriendReqVo vo);

    /**
     * 是否置顶
     * @param vo
     */
    void toTop(WechatFriendReqVo vo);

    /**
     * 是否免打扰
     * @param vo
     */
    void toDisturb(WechatFriendReqVo vo);

}

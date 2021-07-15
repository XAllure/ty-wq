package com.ty.wq.service.client;

import com.ty.wq.dao.client.WechatRoomDao;
import com.ty.wq.pojo.po.client.WechatRoom;
import com.ty.wq.pojo.vo.client.wechatRoom.WechatRoomReqVo;
import com.ty.wq.pojo.vo.client.wechatRoom.WechatRoomRespVo;
import com.ty.wq.service.base.BaseService;
import com.ty.wq.pojo.vo.client.wechatRoom.WechatRoomSearchVo;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author Administrator
 * @version 1.0
 * @program: ty-wq
 * @description:
 * @date 2021-06-21 03:41:23
 */
@Service
public interface WechatRoomService extends BaseService<WechatRoom, WechatRoomDao, WechatRoomSearchVo> {

    /**
     * 通过微信id和群id获取某个群
     * @param wechatId
     * @param chatRoomId
     * @return
     */
    WechatRoom findByWechatIdAndChatRoomId(String wechatId, String chatRoomId);

    /**
     * 获取普通群列表
     * @param wechatId
     * @return
     */
    List<WechatRoomRespVo> getChatRooms(String wechatId);

    /**
     * 删除群
     * @param chatRoomId
     */
    void deleteByChatRoomId(String chatRoomId);

    /**
     * 退出并删除群
     * @param wechatId
     * @param chatRoomId
     */
    void quitDelChatRoom(String wechatId, String chatRoomId);

    /**
     * 修改群名称
     * @param vo
     * @return
     */
    void updateChatRoomName(WechatRoomReqVo vo);

}

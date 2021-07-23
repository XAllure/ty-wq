package com.ty.wq.service.client.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ty.wq.dao.client.WechatRoomDao;
import com.ty.wq.enums.WechatEnum;
import com.ty.wq.pojo.vo.client.wechatRoom.WechatRoomReqVo;
import com.ty.wq.pojo.vo.client.wechatRoom.WechatRoomRespVo;
import com.ty.wq.pojo.vo.client.wechatRoom.WechatRoomSearchVo;
import com.ty.wq.pojo.po.client.WechatRoom;
import com.ty.wq.service.client.WechatRoomMemberService;
import com.ty.wq.service.client.WechatRoomService;
import com.ty.wq.service.base.impl.BaseServiceImpl;
import com.ty.wq.utils.OrikaUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
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
public class WechatRoomServiceImpl extends BaseServiceImpl<WechatRoom, WechatRoomDao, WechatRoomSearchVo> implements WechatRoomService {

    @Lazy
    @Autowired
    private WechatRoomMemberService wechatRoomMemberService;

    /**
     * 获取某个群
     * @param chatRoomId
     * @return
     */
    @Override
    public WechatRoom findByWechatIdAndChatRoomId(String wechatId, String chatRoomId) {
        QueryWrapper<WechatRoom> qw = new QueryWrapper<>();
        qw.eq("wechat_id", wechatId)
                .eq("chat_room_id", chatRoomId)
                .eq("status", WechatEnum.CHATROOM_NORMAL.getCode());
        return findOne(qw);
    }

    /**
     * 获取普通群列表
     * @param wechatId
     * @return
     */
    @Override
    public List<WechatRoomRespVo> getChatRooms(String wechatId) {
        QueryWrapper<WechatRoom> qw = new QueryWrapper<>();
        qw.eq("wechat_id", wechatId)
                .eq("status", WechatEnum.CHATROOM_NORMAL.getCode());
        return OrikaUtils.converts(findList(qw), WechatRoomRespVo.class);
    }

    /**
     * 删除群
     * @param wechatId
     * @param chatRoomId
     */
    @Override
    public void deleteByWechatIdAndChatRoomId(String wechatId, String chatRoomId) {
        QueryWrapper<WechatRoom> qw = new QueryWrapper<>();
        qw.eq("wechat_id", wechatId).eq("chat_room_id", chatRoomId);
        delete(qw);
    }

    /**
     * 退出并删除群
     * @param wechatId
     * @param chatRoomId
     */
    @Override
    public void quitDelChatRoom(String wechatId, String chatRoomId) {
        WechatRoom wechatRoom = findByWechatIdAndChatRoomId(wechatId, chatRoomId);
        if (wechatRoom != null) {
            wechatRoom.setStatus(WechatEnum.CHATROOM_DELETED.getCode());
            // 删除群
            deleteByWechatIdAndChatRoomId(wechatId, chatRoomId);
            // 删除成员
            wechatRoomMemberService.deleteByRoomIdAndChatRoomId(wechatRoom.getId(), wechatRoom.getChatRoomId());
        }
    }

    /**
     * 修改群名称
     * @param vo
     */
    @Override
    public void updateChatRoomName(WechatRoomReqVo vo) {
        WechatRoom wechatRoom = findByWechatIdAndChatRoomId(vo.getWechatId(), vo.getChatRoomId());
        wechatRoom.setChatRoomName(vo.getChatRoomName());
        updateById(wechatRoom);
    }


}

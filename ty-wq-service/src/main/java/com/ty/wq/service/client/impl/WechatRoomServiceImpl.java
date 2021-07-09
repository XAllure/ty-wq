package com.ty.wq.service.client.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ty.wq.constant.MsgType;
import com.ty.wq.constant.OptionKey;
import com.ty.wq.dao.client.WechatRoomDao;
import com.ty.wq.enums.CodeEnum;
import com.ty.wq.enums.WechatEnum;
import com.ty.wq.pojo.po.client.WechatRoomMember;
import com.ty.wq.pojo.vo.Result;
import com.ty.wq.pojo.vo.client.wechatMessage.SendMsg;
import com.ty.wq.pojo.vo.client.wechatRoom.WechatRoomReqVo;
import com.ty.wq.pojo.vo.client.wechatRoom.WechatRoomRespVo;
import com.ty.wq.pojo.vo.client.wechatRoom.WechatRoomSearchVo;
import com.ty.wq.pojo.po.client.WechatRoom;
import com.ty.wq.pojo.vo.netty.Option;
import com.ty.wq.service.client.WechatRoomMemberService;
import com.ty.wq.service.client.WechatRoomService;
import com.ty.wq.service.base.impl.BaseServiceImpl;
import com.ty.wq.utils.OrikaUtils;
import com.ty.wq.utils.RouteUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
     * @param chatRoomId
     */
    @Override
    public void deleteByChatRoomId(String chatRoomId) {
        QueryWrapper<WechatRoom> qw = new QueryWrapper<>();
        qw.eq("chat_room_id", chatRoomId);
        delete(qw);
    }

    /**
     * 根据微信id列表和群id删除微信id列表的群
     * @param wechatIds
     * @param chatRoomId
     */
    @Override
    public void deleteByWechatIdsAndChatRoomId(List<String> wechatIds, String chatRoomId) {
        QueryWrapper<WechatRoom> qw = new QueryWrapper<>();
        qw.eq("chat_room_id", chatRoomId).in("wechat_id", wechatIds);
        delete(qw);
    }

    /**
     * 退出并删除群
     * @param vo
     * @return
     */
    @Override
    public Result quitDelChatRoom(WechatRoomReqVo vo) {
        SendMsg sMsg = new SendMsg();
        sMsg.setApi(MsgType.QUIT_DEL_CHAT_ROOM);
        sMsg.setSendId(vo.getWechatId());
        sMsg.setOption(Option.option().add(OptionKey.ROOM_WXID, vo.getChatRoomId()).getOption());
        // 通知netty服务端
        Result res = RouteUtils.send(sMsg);
        if (res.getCode().equals(CodeEnum.SUCCESS.getCode())) {
            WechatRoom wechatRoom = findByWechatIdAndChatRoomId(vo.getWechatId(), vo.getChatRoomId());
            // 如果自己是群主
            if (wechatRoom.getOwner().equals(vo.getWechatId())) {
                wechatRoom.setStatus(WechatEnum.CHATROOM_DELETED.getCode());
                // 删除群
                deleteByChatRoomId(vo.getChatRoomId());
                // 删除成员
                wechatRoomMemberService.deleteByChatRoomId(wechatRoom.getChatRoomId());
            } else {
                // 如果自己不是群主
                WechatRoomMember wechatRoomMember = wechatRoomMemberService.getByWechatIdAndChatRoomId(vo.getWechatId(), vo.getChatRoomId());
                wechatRoomMember.setStatus(WechatEnum.CHATROOM_DELETED.getCode());
                wechatRoom.setStatus(WechatEnum.CHATROOM_DELETED.getCode());
                // 删除群
                delete(wechatRoom);
                // 退出群聊
                wechatRoomMemberService.delete(wechatRoomMember);
            }
        }
        return res;
    }

    /**
     * 修改群名称
     * @param vo
     * @return
     */
    @Override
    public Result updateChatRoomName(WechatRoomReqVo vo) {
        SendMsg sMsg = new SendMsg();
        sMsg.setApi(MsgType.UPDATE_CHAT_ROOM_NAME);
        sMsg.setSendId(vo.getWechatId());
        sMsg.setOption(Option.option().add(OptionKey.NAME, vo.getChatRoomName()).getOption());
        // 通知netty服务端
        Result res = RouteUtils.send(sMsg);
        if (res.getCode().equals(CodeEnum.SUCCESS.getCode())) {

        }
        return null;
    }


}

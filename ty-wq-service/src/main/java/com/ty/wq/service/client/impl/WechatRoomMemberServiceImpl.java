package com.ty.wq.service.client.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ty.wq.dao.client.WechatRoomMemberDao;
import com.ty.wq.enums.WechatEnum;
import com.ty.wq.pojo.vo.client.wechatRoomMember.WechatRoomMemberReqVo;
import com.ty.wq.pojo.vo.client.wechatRoomMember.WechatRoomMemberSearchVo;
import com.ty.wq.pojo.po.client.WechatRoomMember;
import com.ty.wq.service.client.WechatRoomMemberService;
import com.ty.wq.service.base.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Administrator
 * @version 1.0
 * @program: ty-wq
 * @description:
 * @date 2021-06-21 03:41:36
 */
@Service
public class WechatRoomMemberServiceImpl extends BaseServiceImpl<WechatRoomMember, WechatRoomMemberDao, WechatRoomMemberSearchVo> implements WechatRoomMemberService {

    /**
     * 通过微信id和微信群id查询
     * @param wechatId
     * @param chatRoomId
     * @return
     */
    @Override
    public WechatRoomMember getByRoomIdAndWechatIdAndChatRoomId(Long roomId, String wechatId, String chatRoomId) {
        QueryWrapper<WechatRoomMember> qw =new QueryWrapper<>();
        qw.eq("room_id", roomId)
                .eq("wechat_id", wechatId)
                .eq("chat_room_id", chatRoomId)
                .eq("status", WechatEnum.CHATROOM_MEMBER_NORMAL.getCode());
        return findOne(qw);
    }

    /**
     * 获取单个普通群成员信息
     * @param vo
     * @return
     */
    @Override
    public List<WechatRoomMember> getSingleChatRoomMembers(WechatRoomMemberReqVo vo) {
        QueryWrapper<WechatRoomMember> qw =new QueryWrapper<>();
        qw.eq("room_id", vo.getRoomId())
                .eq("chat_room_id", vo.getChatRoomId())
                .eq("status", WechatEnum.CHATROOM_MEMBER_NORMAL.getCode());
        return findList(qw);
    }

    /**
     * 删除群时根据群id删除群成员
     * @param chatRoomId
     */
    @Override
    public void deleteByRoomIdAndChatRoomId(Long roomId, String chatRoomId) {
        QueryWrapper<WechatRoomMember> qw = new QueryWrapper<>();
        qw.eq("room_id", roomId).eq("chat_room_id", chatRoomId);
        delete(qw);
    }

    /**
     * 根据微信id列表和群id删除成员
     * @param wechatIds
     * @param chatRoomId
     */
    @Override
    public void deleteByWechatIdsAndChatRoomId(List<String> wechatIds, String chatRoomId) {
        QueryWrapper<WechatRoomMember> qw = new QueryWrapper<>();
        qw.eq("chat_room_id", chatRoomId).in("wechat_id", wechatIds);
        delete(qw);
    }

    /**
     * 修改我在本群的昵称
     * @param vo
     */
    @Override
    public void updateChatRoomDisplayName(WechatRoomMemberReqVo vo) {
        QueryWrapper<WechatRoomMember> qw = new QueryWrapper<>();
        qw.eq("chat_room_id", vo.getChatRoomId())
                .eq("wechat_id", vo.getWechatId());
        List<WechatRoomMember> members = findList(qw);
        for (WechatRoomMember member : members) {
            member.setDisplayName(vo.getDisplayName());
            updateById(member);
        }
    }
}

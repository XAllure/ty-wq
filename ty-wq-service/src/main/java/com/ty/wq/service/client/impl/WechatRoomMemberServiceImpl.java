package com.ty.wq.service.client.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ty.wq.constant.ApiType;
import com.ty.wq.constant.OptionKey;
import com.ty.wq.dao.client.WechatRoomMemberDao;
import com.ty.wq.enums.CodeEnum;
import com.ty.wq.enums.WechatEnum;
import com.ty.wq.pojo.vo.Result;
import com.ty.wq.pojo.vo.client.wechatMessage.SendMsg;
import com.ty.wq.pojo.vo.client.wechatRoomMember.WechatRoomMemberReqVo;
import com.ty.wq.pojo.vo.client.wechatRoomMember.WechatRoomMemberSearchVo;
import com.ty.wq.pojo.po.client.WechatRoomMember;
import com.ty.wq.pojo.vo.netty.Option;
import com.ty.wq.service.client.WechatRoomMemberService;
import com.ty.wq.service.base.impl.BaseServiceImpl;
import com.ty.wq.service.client.WechatRoomService;
import com.ty.wq.utils.RouteUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private WechatRoomService wechatRoomService;

    /**
     * 通过微信id和群id查询
     *
     * @param wechatId
     * @param chatRoomId
     * @return
     */
    @Override
    public WechatRoomMember getByWechatIdAndChatRoomId(String wechatId, String chatRoomId) {
        QueryWrapper<WechatRoomMember> qw =new QueryWrapper<>();
        qw.eq("wechat_id", wechatId)
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
    public WechatRoomMember getSingleChatRoomMembers(WechatRoomMemberReqVo vo) {
        return getByWechatIdAndChatRoomId(vo.getWechatId(), vo.getChatRoomId());
    }

    /**
     * 删除群时根据群id删除群成员
     * @param chatRoomId
     */
    @Override
    public void deleteByChatRoomId(String chatRoomId) {
        QueryWrapper<WechatRoomMember> qw = new QueryWrapper<>();
        qw.eq("chat_room_id", chatRoomId);
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
     * 踢群成员
     * @param vo
     * @return res
     */
    @Override
    public Result delChatRoomMembers(WechatRoomMemberReqVo vo) {
        SendMsg sMsg = new SendMsg();
        sMsg.setApi(ApiType.DEL_CHAT_ROOM_MEMBERS);
        sMsg.setSendId(vo.getWechatId());
        sMsg.setOption(Option.option()
                .add(OptionKey.WXID_LIST, vo.getWxidList())
                .add(OptionKey.ROOM_WXID, vo.getChatRoomId())
                .getOption());
        Result res = RouteUtils.send(sMsg);
        if (res.getCode().equals(CodeEnum.SUCCESS.getCode())) {
            wechatRoomService.deleteByWechatIdsAndChatRoomId(vo.getWxidList(), vo.getChatRoomId());
            deleteByWechatIdsAndChatRoomId(vo.getWxidList(), vo.getChatRoomId());
        }
        return res;
    }

    /**
     * 修改我在本群的昵称
     * @param vo
     * @return
     */
    @Override
    public Result updateChatRoomDisplayName(WechatRoomMemberReqVo vo) {
        SendMsg sMsg = new SendMsg();
        sMsg.setApi(ApiType.UPDATE_CHAT_ROOM_DISPLAY_NAME);
        sMsg.setSendId(vo.getWechatId());
        sMsg.setOption(Option.option()
                .add(OptionKey.ROOM_WXID, vo.getChatRoomId())
                .add(OptionKey.NICK, vo.getDisplayName())
                .getOption());
        // 通知netty服务端
        Result res = RouteUtils.send(sMsg);
        return null;
    }
}

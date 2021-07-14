package com.ty.wq.handler.report;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ty.wq.enums.WechatEnum;
import com.ty.wq.pojo.po.client.WechatRoom;
import com.ty.wq.pojo.po.client.WechatRoomMember;
import com.ty.wq.pojo.vo.client.wechatMessage.ReceiveMsg;
import com.ty.wq.pojo.vo.client.wechatRoomMember.WechatRoomMemberRespVo;
import com.ty.wq.service.client.WechatRoomMemberService;
import com.ty.wq.service.client.WechatRoomService;
import com.ty.wq.utils.OrikaUtils;
import com.ty.wq.utils.SendUtils;
import com.ty.wq.vo.ChatRoomMemberVo;
import com.ty.wq.vo.ChatRoomMembersVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 */
@Service
@Slf4j
public class ChatRoomMemberHandler {

    @Autowired
    private WechatRoomService wechatRoomService;

    @Autowired
    private WechatRoomMemberService wechatRoomMemberService;

    /**
     * 获取单个普通群成员信息
     * @param rMsg
     */
    @Async
    public void singleChatRoomMembersHandler(ReceiveMsg rMsg) {
        JSONObject data = JSON.parseObject(String.valueOf(rMsg.getData()));
        ChatRoomMembersVo membersVo = OrikaUtils.convert(data, ChatRoomMembersVo.class);
        JSONArray array = JSON.parseArray(membersVo.getUserLists());
        List<ChatRoomMemberVo> memberVos = OrikaUtils.converts(array, ChatRoomMemberVo.class);
        List<WechatRoomMember> members = new ArrayList<>();
        for (ChatRoomMemberVo memberVo : memberVos) {
            WechatRoomMember member = wechatRoomMemberService.getByWechatIdAndChatRoomId(memberVo.getWxid(), membersVo.getWxid());
            if (member != null) {
                setChatRoomMembers(rMsg.getCwxid(), membersVo, memberVo, member);
                wechatRoomMemberService.updateById(member);
            } else {
                member = new WechatRoomMember();
                member.setChatRoomId(membersVo.getWxid());
                member.setStatus(WechatEnum.CHATROOM_MEMBER_NORMAL.getCode());
                setChatRoomMembers(rMsg.getCwxid(), membersVo, memberVo, member);
                wechatRoomMemberService.insert(member);
            }
            members.add(member);
        }
        /*List<WechatRoomMemberRespVo> respVos = OrikaUtils.converts(members, WechatRoomMemberRespVo.class);
        SendUtils.send(rMsg.getCwxid(), rMsg.getAction(), respVos);*/
    }

    private void setChatRoomMembers(String wechatId, ChatRoomMembersVo membersVo, ChatRoomMemberVo memberVo, WechatRoomMember member) {
        // 获取群主id
        WechatRoom wechatRoom = wechatRoomService.findByWechatIdAndChatRoomId(wechatId, membersVo.getWxid());
        member.setOwnerWechatId(wechatRoom.getOwner());
        member.setWechatId(memberVo.getWxid());
        member.setWechatNo(memberVo.getAlias());
        member.setWechatNick(memberVo.getNick());
        member.setHeadPic(memberVo.getHeadPic());
        member.setGender(memberVo.getSex());
        member.setCountry(memberVo.getCountry());
        member.setProvince(memberVo.getProvince());
        member.setCity(memberVo.getCity());
        member.setDisplayName(memberVo.getDisplayname());
        member.setRemark(memberVo.getRemark());
        member.setCompanyId(wechatRoom.getCompanyId());
        member.setDepartmentId(wechatRoom.getDepartmentId());
    }

}

package com.ty.wq.handler.report;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ty.wq.constant.ApiType;
import com.ty.wq.constant.MsgType;
import com.ty.wq.constant.OptionKey;
import com.ty.wq.enums.WechatEnum;
import com.ty.wq.pojo.po.client.Wechat;
import com.ty.wq.pojo.po.client.WechatRoom;
import com.ty.wq.pojo.po.client.WechatRoomMember;
import com.ty.wq.pojo.vo.client.wechatMessage.ReceiveMsg;
import com.ty.wq.pojo.vo.client.wechatMessage.SendMsg;
import com.ty.wq.pojo.vo.client.wechatRoom.WechatRoomRespVo;
import com.ty.wq.pojo.vo.netty.Message;
import com.ty.wq.pojo.vo.netty.MsgVo;
import com.ty.wq.pojo.vo.netty.Option;
import com.ty.wq.service.client.WechatRoomMemberService;
import com.ty.wq.service.client.WechatRoomService;
import com.ty.wq.service.client.WechatService;
import com.ty.wq.utils.ChannelUtils;
import com.ty.wq.utils.MsgUtils;
import com.ty.wq.utils.OrikaUtils;
import com.ty.wq.utils.SendUtils;
import com.ty.wq.pojo.vo.netty.report.ChatRoomMemberVo;
import com.ty.wq.pojo.vo.netty.report.ChatRoomVo;
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
public class WechatChatRoomHandler {

    @Autowired
    private WechatService wechatService;

    @Autowired
    private WechatRoomService wechatRoomService;

    @Autowired
    private WechatRoomMemberService wechatRoomMemberService;

    /**
     * 上报普通群列表
     * @param rMsg
     */
    @Async
    public void chatRoomsHandler(ReceiveMsg rMsg) {
        JSONObject data = JSON.parseObject(String.valueOf(rMsg.getData()));
        // 获取好友列表
        JSONArray groupList = data.getJSONArray("groupList");
        List<ChatRoomVo> roomVos = OrikaUtils.converts(groupList, ChatRoomVo.class);
        List<WechatRoom> wechatRooms = new ArrayList<>();
        // 暂时一个个循环判断
        for (ChatRoomVo roomVo : roomVos) {
            getMembers(rMsg.getCwxid(), roomVo.getWxid());
            WechatRoom wechatRoom = wechatRoomService.findByWechatIdAndChatRoomId(rMsg.getCwxid(), roomVo.getWxid());
            // 已存在
            if (wechatRoom != null) {
                setWechatRoom(rMsg.getCwxid(), roomVo, wechatRoom);
                // 更新
                wechatRoomService.updateById(wechatRoom);
            } else {
                //不存在 赋值
                wechatRoom = new WechatRoom();
                wechatRoom.setWechatId(rMsg.getCwxid());
                wechatRoom.setChatRoomId(roomVo.getWxid());
                setWechatRoom(rMsg.getCwxid(), roomVo, wechatRoom);
                // 插入
                wechatRoomService.insert(wechatRoom);
            }
            wechatRooms.add(wechatRoom);
        }
        List<WechatRoomRespVo> respVos = OrikaUtils.converts(wechatRooms, WechatRoomRespVo.class);
        SendUtils.send(rMsg.getCwxid(), rMsg.getAction(), respVos);
    }

    /**
     * 获取单个普通群成员信息
     * @param wechatId
     * @param chatRoomId
     */
    private void getMembers(String wechatId, String chatRoomId) {
        SendMsg sMsg = new SendMsg();
        sMsg.setApi(ApiType.GET_SINGLE_CHAT_ROOM_MEMBERS);
        sMsg.setSendId(wechatId);
        sMsg.setOption(Option.option()
                .add(OptionKey.ROOM_WXID, chatRoomId)
                .getOption());
        MsgVo msgVo = new MsgVo();
        msgVo.setType(MsgType.SEND_MSG);
        msgVo.setData(sMsg);
        MsgUtils.writeJson(ChannelUtils.getClientChannel(wechatId), Message.success(msgVo));
    }

    /**
     * 上报群成员新增通知
     * @param rMsg
     */
    @Async
    public void chatRoomMemberAddHandler(ReceiveMsg rMsg) {
        JSONObject data = JSON.parseObject(String.valueOf(rMsg.getData()));
        ChatRoomVo roomVo = OrikaUtils.convert(data, ChatRoomVo.class);
        getMembers(rMsg.getCwxid(), roomVo.getWxid());
        WechatRoom wechatRoom = wechatRoomService.findByWechatIdAndChatRoomId(rMsg.getCwxid(), roomVo.getWxid());
        // 赋值
        setWechatRoom(rMsg.getCwxid(), roomVo, wechatRoom);
        // 更新
        wechatRoomService.updateById(wechatRoom);
        // 发送邀请人邀请入群信息
        SendUtils.send(rMsg.getCwxid(), rMsg.getAction(), JSON.parseArray(roomVo.getUserLists()));
    }

    /**
     * 上报群成员删除通知
     * @param rMsg
     */
    @Async
    public void chatRoomMemberDelHandler(ReceiveMsg rMsg) {
        JSONObject data = JSON.parseObject(String.valueOf(rMsg.getData()));
        ChatRoomVo roomVo = OrikaUtils.convert(data, ChatRoomVo.class);
        WechatRoom wechatRoom = wechatRoomService.findByWechatIdAndChatRoomId(rMsg.getCwxid(), roomVo.getWxid());
        // 赋值
        setWechatRoom(rMsg.getCwxid(), roomVo, wechatRoom);
        // 更新
        wechatRoomService.updateById(wechatRoom);
        // 列出被删除的成员
        JSONArray userLists = JSON.parseArray(roomVo.getUserLists());
        List<ChatRoomMemberVo> memberVos = OrikaUtils.converts(userLists, ChatRoomMemberVo.class);
        List<String> wechatIds = new ArrayList<>();
        for (ChatRoomMemberVo memberVo : memberVos) {
            wechatIds.add(memberVo.getWxid());
        }
        // 删除群成员
        wechatRoomMemberService.deleteByWechatIdsAndChatRoomId(wechatIds, roomVo.getWxid());
        // 通知
        SendUtils.send(rMsg.getCwxid(), rMsg.getAction(), userLists);
    }

    /**
     * 上报检测到的新群通知
     * @param rMsg
     */
    @Async
    public void newChatRoomHandler(ReceiveMsg rMsg) {
        JSONObject data = JSON.parseObject(String.valueOf(rMsg.getData()));
        ChatRoomVo roomVo = OrikaUtils.convert(data, ChatRoomVo.class);
        getMembers(rMsg.getCwxid(), roomVo.getWxid());
        WechatRoom wechatRoom = new WechatRoom();
        wechatRoom.setWechatId(rMsg.getCwxid());
        wechatRoom.setChatRoomId(roomVo.getWxid());
        setWechatRoom(rMsg.getCwxid(), roomVo, wechatRoom);
        // 插入
        wechatRoomService.insert(wechatRoom);
        // 通知
        SendUtils.send(rMsg.getCwxid(), rMsg.getAction(), roomVo);
    }

    /**
     * 上报退群或被踢通知
     * @param rMsg
     */
    @Async
    public void chatRoomQuitHandler(ReceiveMsg rMsg) {
        JSONObject data = JSON.parseObject(String.valueOf(rMsg.getData()));
        String chatRoomId = data.getString("roomWxid");
        wechatRoomService.quitDelChatRoom(rMsg.getCwxid(), chatRoomId);
        // 通知
        SendUtils.send(rMsg.getCwxid(), rMsg.getAction(), chatRoomId);
    }



    /**
     * 赋值
     * @param roomVo
     * @param wechatRoom
     */
    private void setWechatRoom(String wechatId, ChatRoomVo roomVo, WechatRoom wechatRoom) {
        // 查询微信信息
        Wechat wechat = wechatService.findByWechatId(wechatId);
        // 查询群主信息
        WechatRoomMember owner = wechatRoomMemberService.getByWechatIdAndChatRoomId(roomVo.getOwner(), roomVo.getWxid());
        // 赋值
        wechatRoom.setChatRoomName(roomVo.getNick());
        wechatRoom.setRoomMemberCount(roomVo.getRoomCount());
        wechatRoom.setAvatar(roomVo.getHeadPic());
        wechatRoom.setOwner(roomVo.getOwner());
        wechatRoom.setOwnerNickName(owner == null ? "":owner.getWechatNick());
        wechatRoom.setIsOwner(roomVo.getIsowner());
        wechatRoom.setCompanyId(wechat.getCompanyId());
        wechatRoom.setDepartmentId(wechat.getDepartmentId());
        wechatRoom.setStatus(WechatEnum.CHATROOM_NORMAL.getCode());
        wechatRoom.setDeleted(0);
    }

}

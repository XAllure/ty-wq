package com.ty.wq.controller;

import com.ty.wq.constant.ApiType;
import com.ty.wq.constant.OptionKey;
import com.ty.wq.pojo.po.client.WechatRoomMember;
import com.ty.wq.pojo.vo.BaseReqVo;
import com.ty.wq.pojo.vo.Result;
import com.ty.wq.pojo.vo.client.wechatMessage.SendMsg;
import com.ty.wq.pojo.vo.client.wechatRoomMember.WechatRoomMemberReqVo;
import com.ty.wq.pojo.vo.client.wechatRoomMember.WechatRoomMemberRespVo;
import com.ty.wq.pojo.vo.netty.Option;
import com.ty.wq.service.client.WechatRoomMemberService;
import com.ty.wq.utils.OrikaUtils;
import com.ty.wq.utils.ReqVoUtils;
import com.ty.wq.utils.RouteUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("/wechat/room/member")
public class WechatRoomMemberController {

    @Autowired
    private WechatRoomMemberService wechatRoomMemberService;

    /**
     * 获取单个普通群成员信息
     * @param vo
     * @return
     */
    @PostMapping("/getSingleChatRoomMembers")
    public Result getSingleChatRoomMembers(@RequestBody WechatRoomMemberReqVo vo) {
        ReqVoUtils.validated(vo, BaseReqVo.Info.class);
        WechatRoomMember wechatRoomMember = wechatRoomMemberService.getSingleChatRoomMembers(vo);
        WechatRoomMemberRespVo respVo = OrikaUtils.convert(wechatRoomMember, WechatRoomMemberRespVo.class);
        return Result.success(respVo);
    }

    /**
     * 踢群成员 当前微信必须有踢人权限
     * @param vo
     * @return
     */
    @PostMapping("/delChatRoomMembers")
    public Result delChatRoomMembers(@RequestBody WechatRoomMemberReqVo vo) {
        ReqVoUtils.validated(vo, BaseReqVo.Out.class);
        return wechatRoomMemberService.delChatRoomMembers(vo);
    }

    /**
     * 修改我在本群的昵称
     * @param vo
     * @return
     */
    @PostMapping("/updateChatRoomDisplayName")
    public Result updateChatRoomDisplayName(@RequestBody WechatRoomMemberReqVo vo) {
        ReqVoUtils.validated(vo, BaseReqVo.Update.class);
        return wechatRoomMemberService.updateChatRoomDisplayName(vo);
    }

    /**
     * 发送40人以下群邀请 不需要好友同意即可直接拉入群
     * @param vo
     * @return
     */
    @PostMapping("/sendChatroomLow")
    public Result sendChatroomLow(@RequestBody WechatRoomMemberReqVo vo) {
        ReqVoUtils.validated(vo, BaseReqVo.Invite.class);
        SendMsg sMsg = new SendMsg();
        sMsg.setApi(ApiType.SEND_CHATROOM_LOW);
        sMsg.setSendId(vo.getWechatId());
        sMsg.setOption(Option.option()
                .add(OptionKey.ROOM_WXID, vo.getChatRoomId())
                .add(OptionKey.WXID_LIST, vo.getWxidList())
                .getOption());
        return RouteUtils.send(sMsg);
    }

    /**
     * 发送40人以上群邀请 需要好友同意
     * @param vo
     * @return
     */
    @PostMapping("/sendChatroomHigh")
    public Result sendChatroomHigh(@RequestBody WechatRoomMemberReqVo vo) {
        ReqVoUtils.validated(vo, BaseReqVo.Invite.class);
        SendMsg sMsg = new SendMsg();
        sMsg.setApi(ApiType.SEND_CHATROOM_HIGH);
        sMsg.setSendId(vo.getWechatId());
        sMsg.setOption(Option.option()
                .add(OptionKey.ROOM_WXID, vo.getChatRoomId())
                .add(OptionKey.WXID_LIST, vo.getWxidList())
                .getOption());
        return RouteUtils.send(sMsg);
    }

}

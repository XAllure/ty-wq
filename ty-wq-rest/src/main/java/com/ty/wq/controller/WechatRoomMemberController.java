package com.ty.wq.controller;

import com.ty.wq.anno.VxPermission;
import com.ty.wq.constant.ApiType;
import com.ty.wq.constant.OptionKey;
import com.ty.wq.enums.CodeEnum;
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
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("/wechat/room/member")
@Api(tags = "微信群成员相关")
@VxPermission(prefix = "room:member")
public class WechatRoomMemberController {

    @Autowired
    private WechatRoomMemberService wechatRoomMemberService;

    @ApiOperation(value = "获取单个普通群成员信息")
    @PostMapping("/getSingleChatRoomMembers")
    public Result getSingleChatRoomMembers(@RequestBody WechatRoomMemberReqVo vo) {
        ReqVoUtils.validated(vo, BaseReqVo.Info.class);
        List<WechatRoomMember> wechatRoomMember = wechatRoomMemberService.getSingleChatRoomMembers(vo);
        List<WechatRoomMemberRespVo> respVo = OrikaUtils.converts(wechatRoomMember, WechatRoomMemberRespVo.class);
        return Result.success(respVo);
    }

    @ApiOperation(value = "踢群成员 当前微信必须有踢人权限")
    @PostMapping("/delChatRoomMembers")
    public Result delChatRoomMembers(@RequestBody WechatRoomMemberReqVo vo) {
        ReqVoUtils.validated(vo, BaseReqVo.Out.class);
        SendMsg sMsg = new SendMsg();
        sMsg.setApi(ApiType.DEL_CHAT_ROOM_MEMBERS);
        sMsg.setSendId(vo.getWechatId());
        sMsg.setOption(Option.option()
                .add(OptionKey.WXID_LIST, vo.getWxidList())
                .add(OptionKey.ROOM_WXID, vo.getChatRoomId())
                .getOption());
        return RouteUtils.send(sMsg);
    }

    /**
     * 修改我在本群的昵称
     * @param vo
     * @return
     */
    @ApiOperation(value = "修改我在本群的昵称")
    @PostMapping("/updateChatRoomDisplayName")
    public Result updateChatRoomDisplayName(@RequestBody WechatRoomMemberReqVo vo) {
        ReqVoUtils.validated(vo, BaseReqVo.Update.class);
        SendMsg sMsg = new SendMsg();
        sMsg.setApi(ApiType.UPDATE_CHAT_ROOM_DISPLAY_NAME);
        sMsg.setSendId(vo.getWechatId());
        sMsg.setOption(Option.option()
                .add(OptionKey.ROOM_WXID, vo.getChatRoomId())
                .add(OptionKey.NICK, vo.getDisplayName())
                .getOption());
        // 通知netty服务端
        Result res = RouteUtils.send(sMsg);
        if (res.getCode().equals(CodeEnum.SUCCESS.getCode())) {
            wechatRoomMemberService.updateChatRoomDisplayName(vo);
        }
        return res;
    }

    @ApiOperation(value = "发送40人以下群邀请 不需要好友同意即可直接拉入群")
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
    @ApiOperation(value = "发送40人以上群邀请 需要好友同意")
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

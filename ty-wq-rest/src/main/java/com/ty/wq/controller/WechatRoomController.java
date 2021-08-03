package com.ty.wq.controller;

import com.ty.wq.anno.VxPermission;
import com.ty.wq.constant.ApiType;
import com.ty.wq.constant.OptionKey;
import com.ty.wq.enums.CodeEnum;
import com.ty.wq.pojo.vo.BaseReqVo;
import com.ty.wq.pojo.vo.Result;
import com.ty.wq.pojo.vo.client.wechatMessage.SendMsg;
import com.ty.wq.pojo.vo.client.wechatRoom.AcceptChatroomReqVo;
import com.ty.wq.pojo.vo.client.wechatRoom.WechatRoomReqVo;
import com.ty.wq.pojo.vo.client.wechatRoom.WechatRoomRespVo;
import com.ty.wq.pojo.vo.netty.Option;
import com.ty.wq.service.client.WechatRoomService;
import com.ty.wq.utils.ReqVoUtils;
import com.ty.wq.utils.RouteUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("/wechat/room")
@Api(tags = "微信群相关")
@VxPermission(prefix = "room")
public class WechatRoomController {

    @Autowired
    private WechatRoomService wechatRoomService;

    @ApiOperation(value = "获取普通群列表")
    @PostMapping("/getChatRooms")
    public Result getChatRooms(@RequestBody WechatRoomReqVo vo) {
        ReqVoUtils.validated(vo, BaseReqVo.Get.class);
        SendMsg sMsg = new SendMsg();
        sMsg.setApi(ApiType.GET_CHAT_ROOMS);
        sMsg.setSendId(vo.getWechatId());
        return RouteUtils.send(sMsg);
    }

    @ApiOperation(value = "根据微信id获取普通群列表")
    @PostMapping("/getChatRooms/{wechatId}")
    public Result getChatRooms(@Valid @NotBlank(message = "微信id参数错误") @PathVariable String wechatId) {
        List<WechatRoomRespVo> vos = wechatRoomService.getChatRooms(wechatId);
        return Result.success(vos);
    }

    @ApiOperation(value = "创建群聊")
    @PostMapping("/createChatRoom")
    @VxPermission("create")
    public Result createChatRoom(@RequestBody WechatRoomReqVo vo) {
        ReqVoUtils.validated(vo, BaseReqVo.Add.class);
        SendMsg sMsg = new SendMsg();
        sMsg.setApi(ApiType.CREATE_CHAT_ROOM);
        sMsg.setSendId(vo.getWechatId());
        sMsg.setOption(Option.option()
                .add(OptionKey.WXID_LISTS, vo.getWxidLists())
                .getOption());
        return RouteUtils.send(sMsg);
    }

    @ApiOperation(value = "退出并删除群")
    @PostMapping("/quitDelChatRoom")
    public Result quitDelChatRoom(@RequestBody WechatRoomReqVo vo) {
        ReqVoUtils.validated(vo, BaseReqVo.Delete.class);
        SendMsg sMsg = new SendMsg();
        sMsg.setApi(ApiType.QUIT_DEL_CHAT_ROOM);
        sMsg.setSendId(vo.getWechatId());
        sMsg.setOption(Option.option()
                .add(OptionKey.ROOM_WXID, vo.getChatRoomId())
                .getOption());
        // 通知netty服务端
        Result res = RouteUtils.send(sMsg);
        // wechatRoomService.quitDelChatRoom(vo.getWechatId(), vo.getChatRoomId());
        return res;
    }

    @ApiOperation(value = "修改群名称")
    @PostMapping("/updateChatRoomName")
    public Result updateChatRoomName(@RequestBody WechatRoomReqVo vo) {
        ReqVoUtils.validated(vo, BaseReqVo.Delete.class);
        SendMsg sMsg = new SendMsg();
        sMsg.setApi(ApiType.UPDATE_CHAT_ROOM_NAME);
        sMsg.setSendId(vo.getWechatId());
        sMsg.setOption(Option.option()
                .add(OptionKey.ROOM_WXID, vo.getChatRoomId())
                .add(OptionKey.NAME, vo.getChatRoomName())
                .getOption());
        // 通知netty服务端
        Result res = RouteUtils.send(sMsg);
        if (res.getCode().equals(CodeEnum.SUCCESS.getCode())) {
            wechatRoomService.updateChatRoomName(vo);
        }
        return res;
    }

    @ApiOperation(value = "接受群邀请")
    @PostMapping("/acceptChatroomInvite")
    public Result acceptChatroomInvite(@RequestBody AcceptChatroomReqVo vo) {
        ReqVoUtils.validated(vo, BaseReqVo.Invite.class);
        SendMsg sMsg = new SendMsg();
        sMsg.setApi(ApiType.ACCEPT_CHATROOM_INVITE);
        sMsg.setSendId(vo.getWechatId());
        sMsg.setOption(Option.option()
                .add(OptionKey.WXID, vo.getFriendId())
                .add(OptionKey.INVITE_URL, vo.getInviteUrl())
                .getOption());
        return RouteUtils.send(sMsg);
    }

}

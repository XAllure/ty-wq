package com.ty.wq.controller;

import com.ty.wq.anno.VxPermission;
import com.ty.wq.constant.ApiType;
import com.ty.wq.constant.OptionKey;
import com.ty.wq.enums.CodeEnum;
import com.ty.wq.enums.WechatEnum;
import com.ty.wq.pojo.po.client.WechatFriend;
import com.ty.wq.pojo.vo.BaseReqVo;
import com.ty.wq.pojo.vo.Result;
import com.ty.wq.pojo.vo.client.wechatFriend.WechatFriendReqVo;
import com.ty.wq.pojo.vo.client.wechatFriend.WechatFriendRespVo;
import com.ty.wq.pojo.vo.client.wechatMessage.SendMsg;
import com.ty.wq.pojo.vo.netty.Option;
import com.ty.wq.service.client.WechatFriendService;
import com.ty.wq.utils.ReqVoUtils;
import com.ty.wq.utils.RouteUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("/wechat/friend")
@Api(tags = "微信好友相关")
@VxPermission(prefix = "friend")
public class WechatFriendController {

    @Autowired
    private WechatFriendService wechatFriendService;

    @ApiOperation(value = "获取普通好友列表")
    @PostMapping("/getContacts/{wechatId}")
    public Result getContacts(@Valid @NotBlank(message = "微信id参数错误") @PathVariable String wechatId) {
        List<WechatFriendRespVo> vos = wechatFriendService.getWechatFriends(wechatId);
        /*SendMsg sMsg = new SendMsg();
        sMsg.setApi(ApiType.GET_CONTACTS);
        sMsg.setSendId(wechatId);
        RouteUtils.send(sMsg);*/
        return Result.success(vos);
    }

    @ApiOperation(value = "获取微信好友详细信息")
    @PostMapping("/getSingleContact")
    public Result getSingleContact(@RequestBody WechatFriendReqVo vo) {
        ReqVoUtils.validated(vo, BaseReqVo.Info.class);
        return Result.success(wechatFriendService.getFriendInfo(vo));
    }

    @ApiOperation(value = "任意普通微信反查详细信息")
    @PostMapping("/updateContact")
    public Result updateContact(@RequestBody WechatFriendReqVo vo) {
        ReqVoUtils.validated(vo, BaseReqVo.Info.class);
        SendMsg sMsg = new SendMsg();
        sMsg.setApi(ApiType.UPDATE_CONTACT);
        sMsg.setSendId(vo.getWechatId());
        sMsg.setOption(Option.option()
                .add(OptionKey.WXID, vo.getFriendId())
                .getOption());
        return RouteUtils.send(sMsg);
    }

    @ApiOperation(value = "添加好友(不支持通过查询添加，即 scene = 15)")
    @PostMapping("/addFriend")
    public Result addFriend(@RequestBody WechatFriendReqVo vo) {
        ReqVoUtils.validated(vo, BaseReqVo.Add.class);
        if (vo.getScene().equals(WechatEnum.SCENE_CARD.getCode())) {
            if (StringUtils.isBlank(vo.getV1())) {
                return Result.error(CodeEnum.ERROR.getCode(), "请传入v1值");
            }
        }
        SendMsg sendMsg = new SendMsg();
        sendMsg.setApi(ApiType.ADD_FRIEND);
        sendMsg.setSendId(vo.getWechatId());
        sendMsg.setOption(Option.option()
                .add(OptionKey.WXID, vo.getFriendId())
                .add(OptionKey.REMARK, vo.getRemark())
                .add(OptionKey.SCENE, vo.getScene())
                .add(OptionKey.ROOM_WXID, vo.getChatRoomId())
                .getOption());
        // 通知netty服务端
        Result res = RouteUtils.send(sendMsg);
        if (res.getCode().equals(CodeEnum.SUCCESS.getCode())) {
            wechatFriendService.addFriend(vo);
        }
        return res;
    }

    @ApiOperation(value = "删除好友")
    @PostMapping("/delFriend")
    public Result delFriend(@RequestBody WechatFriendReqVo vo) {
        ReqVoUtils.validated(vo, BaseReqVo.Delete.class);
        SendMsg sendMsg = new SendMsg();
        sendMsg.setApi(ApiType.DEL_FRIEND);
        sendMsg.setSendId(vo.getWechatId());
        sendMsg.setOption(Option.option()
                .add(OptionKey.WXID, vo.getFriendId())
                .getOption());
        // 通知netty服务端
        Result res = RouteUtils.send(sendMsg);
        if (res.getCode().equals(CodeEnum.SUCCESS.getCode())) {
            WechatFriend friend = wechatFriendService.getByWechatIdAndFriendId(vo.getWechatId(), vo.getFriendId());
            friend.setStatus(WechatEnum.FRIEND_DELETED.getCode());
            wechatFriendService.updateById(friend);
        }
        return res;
    }

    @ApiOperation(value = "修改好友备注")
    @PostMapping("/updateRemark")
    public Result updateRemark(@RequestBody WechatFriendReqVo vo) {
        ReqVoUtils.validated(vo, BaseReqVo.Update.class);
        SendMsg sendMsg = new SendMsg();
        sendMsg.setApi(ApiType.UPDATE_REMARK);
        sendMsg.setSendId(vo.getWechatId());
        sendMsg.setOption(Option.option()
                .add(OptionKey.WXID, vo.getFriendId())
                .add(OptionKey.REMARK, vo.getRemarkName())
                .getOption());
        // 通知netty服务端
        Result res = RouteUtils.send(sendMsg);
        if (res.getCode().equals(CodeEnum.SUCCESS.getCode())) {
            wechatFriendService.updateRemark(vo);
        }
        return res;
    }

    @ApiOperation(value = "接受加好友请求")
    @PostMapping("/acceptFriend")
    public Result acceptFriend(@RequestBody WechatFriendReqVo vo) {
        ReqVoUtils.validated(vo, BaseReqVo.Update.class);
        SendMsg sMsg = new SendMsg();
        sMsg.setApi(ApiType.ACCEPT_FRIEND);
        sMsg.setSendId(vo.getWechatId());
        sMsg.setOption(Option.option()
                .add(OptionKey.V1, vo.getV1())
                .add(OptionKey.V2, vo.getV2())
                .add(OptionKey.SCENE, vo.getScene())
                .getOption());
        return RouteUtils.send(sMsg);
    }

    @ApiOperation(value = "通过手机号/微信号/QQ号查询任意微信号信息")
    @PostMapping("/searchContact")
    public Result searchContact(@RequestBody WechatFriendReqVo vo) {
        ReqVoUtils.validated(vo, BaseReqVo.Info.class);
        SendMsg sMsg = new SendMsg();
        sMsg.setApi(ApiType.SEARCH_CONTACT);
        sMsg.setSendId(vo.getWechatId());
        sMsg.setOption(Option.option()
                .add(OptionKey.SEARCH, vo.getFriendId())
                .getOption());
        return RouteUtils.send(sMsg);
    }

    @ApiOperation(value = "添加通过任意手机号/微信号/QQ号查询的联系人(不支持，不可用)")
    @PostMapping("/addSearchContact")
    public Result addSearchContact(@RequestBody WechatFriendReqVo vo) {
        ReqVoUtils.validated(vo, BaseReqVo.Add.class);
        SendMsg sendMsg = new SendMsg();
        sendMsg.setApi(ApiType.ADD_SEARCH_CONTACT);
        sendMsg.setSendId(vo.getWechatId());
        sendMsg.setOption(Option.option()
                .add(OptionKey.V1, vo.getV1())
                .add(OptionKey.V2, vo.getV2())
                .add(OptionKey.REMARK, vo.getRemark())
                .add(OptionKey.SCENE, vo.getScene())
                .getOption());
        // 通知netty服务端
        Result res = RouteUtils.send(sendMsg);
        if (res.getCode().equals(CodeEnum.SUCCESS.getCode())) {
            wechatFriendService.addFriend(vo);
        }
        return res;
    }

    @ApiOperation(value = "修改好友部分信息")
    @PostMapping("/update")
    public Result update(@RequestBody WechatFriendReqVo vo) {
        ReqVoUtils.validated(vo, BaseReqVo.Update.class);
        wechatFriendService.updateFriendInfo(vo);
        return Result.success();
    }

    @ApiOperation(value = "置顶")
    @PostMapping("/top")
    public Result top(@RequestBody WechatFriendReqVo vo) {
        ReqVoUtils.validated(vo, BaseReqVo.Top.class);
        SendMsg sMsg = new SendMsg();
        sMsg.setApi(ApiType.CHAT_SESSION_TOP);
        sMsg.setSendId(vo.getWechatId());
        sMsg.setOption(Option.option()
                .add(OptionKey.WXID, vo.getFriendId())
                .add(OptionKey.CODE, vo.getTop())
                .getOption());
        Result res = RouteUtils.send(sMsg);
        if (res.getCode().equals(CodeEnum.SUCCESS.getCode())) {
            wechatFriendService.toTop(vo);
        }
        return res;
    }

    @ApiOperation(value = "免打扰")
    @PostMapping("/disturb")
    public Result disturb(@RequestBody WechatFriendReqVo vo) {
        ReqVoUtils.validated(vo, BaseReqVo.Disturb.class);
        SendMsg sMsg = new SendMsg();
        sMsg.setApi(ApiType.MOD_RECV_NOTIFY);
        sMsg.setSendId(vo.getWechatId());
        sMsg.setOption(Option.option()
                .add(OptionKey.WXID, vo.getFriendId())
                .add(OptionKey.CODE, vo.getDisturb())
                .getOption());
        Result res = RouteUtils.send(sMsg);
        if (res.getCode().equals(CodeEnum.SUCCESS.getCode())) {
            wechatFriendService.toDisturb(vo);
        }
        return res;
    }

}

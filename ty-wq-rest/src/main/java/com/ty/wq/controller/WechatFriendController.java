package com.ty.wq.controller;

import com.ty.wq.constant.ApiType;
import com.ty.wq.constant.OptionKey;
import com.ty.wq.enums.CodeEnum;
import com.ty.wq.enums.WechatEnum;
import com.ty.wq.pojo.vo.BaseReqVo;
import com.ty.wq.pojo.vo.Result;
import com.ty.wq.pojo.vo.client.wechatFriend.WechatFriendReqVo;
import com.ty.wq.pojo.vo.client.wechatFriend.WechatFriendRespVo;
import com.ty.wq.pojo.vo.client.wechatMessage.SendMsg;
import com.ty.wq.pojo.vo.netty.Option;
import com.ty.wq.service.client.WechatFriendService;
import com.ty.wq.utils.ReqVoUtils;
import com.ty.wq.utils.RouteUtils;
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
public class WechatFriendController {

    @Autowired
    private WechatFriendService wechatFriendService;

    /**
     * 获取普通好友列表
     * @param wechatId 微信id
     * @return
     */
    @PostMapping("/getContacts/{wechatId}")
    public Result getContacts(@Valid @NotBlank(message = "微信id参数错误") @PathVariable String wechatId) {
        List<WechatFriendRespVo> vos = wechatFriendService.getWechatFriends(wechatId);
        return Result.success(vos);
    }

    /**
     * 获取微信好友详细信息
     * @param vo
     * @return
     */
    @PostMapping("/getSingleContact")
    public Result getSingleContact(@RequestBody WechatFriendReqVo vo) {
        ReqVoUtils.validated(vo, BaseReqVo.Info.class);
        return Result.success(wechatFriendService.getFriendInfo(vo));
    }

    /**
     * 任意普通微信反查详细信息
     * @param vo
     * @return
     */
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

    /**
     * 添加好友
     * @return
     */
    @PostMapping("/addFriend")
    public Result addFriend(@RequestBody WechatFriendReqVo vo) {
        ReqVoUtils.validated(vo, BaseReqVo.Add.class);
        if (vo.getScene().equals(WechatEnum.SCENE_CARD.getCode())) {
            if (StringUtils.isBlank(vo.getV1())) {
                return Result.error(CodeEnum.ERROR.getCode(), "请传入v1值");
            }
        }
        return wechatFriendService.addFriend(vo);
    }

    /**
     * 删除好友
     * @param vo
     * @return
     */
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
        return RouteUtils.send(sendMsg);
    }

    /**
     * 修改好友备注
     * @param vo
     * @return
     */
    @PostMapping("/updateRemark")
    public Result updateRemark(@RequestBody WechatFriendReqVo vo) {
        ReqVoUtils.validated(vo, BaseReqVo.Update.class);
        return wechatFriendService.updateRemark(vo);
    }

    /**
     * 接受加好友请求
     * @param vo
     * @return
     */
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

    /**
     * 通过手机号/微信号/QQ号查询任意微信号信息
     * @param vo
     * @return
     */
    @PostMapping("/searchContact")
    public Result searchContact(@RequestBody WechatFriendReqVo vo) {
        ReqVoUtils.validated(vo, BaseReqVo.Info.class, BaseReqVo.Search.class);
        SendMsg sMsg = new SendMsg();
        sMsg.setApi(ApiType.ACCEPT_FRIEND);
        sMsg.setSendId(vo.getWechatId());
        sMsg.setOption(Option.option()
                .add(OptionKey.SEARCH, vo.getSearch())
                .getOption());
        return RouteUtils.send(sMsg);
    }


    @PostMapping("/addSearchContact")
    public Result addSearchContact(@RequestBody WechatFriendReqVo vo) {
        ReqVoUtils.validated(vo, BaseReqVo.Add.class);
        return wechatFriendService.addSearchContact(vo);
    }


    /**
     * 修改好友部分信息
     * @param vo
     * @return
     */
    @PostMapping("/update")
    public Result update(@RequestBody WechatFriendReqVo vo) {
        ReqVoUtils.validated(vo, BaseReqVo.Update.class);
        wechatFriendService.updateFriendInfo(vo);
        return Result.success();
    }

    /**
     * 置顶
     * @param vo
     * @return
     */
    @PostMapping("/top")
    public Result top(@RequestBody WechatFriendReqVo vo) {
        ReqVoUtils.validated(vo, BaseReqVo.Top.class);
        return wechatFriendService.toTop(vo);
    }

    /**
     * 免打扰
     * @param vo
     * @return
     */
    @PostMapping("/disturb")
    public Result disturb(@RequestBody WechatFriendReqVo vo) {
        ReqVoUtils.validated(vo, BaseReqVo.Disturb.class);
        return wechatFriendService.toDisturb(vo);
    }

}

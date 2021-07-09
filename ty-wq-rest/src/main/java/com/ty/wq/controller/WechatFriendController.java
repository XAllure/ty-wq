package com.ty.wq.controller;

import com.ty.wq.constant.MsgType;
import com.ty.wq.constant.OptionKey;
import com.ty.wq.enums.CodeEnum;
import com.ty.wq.enums.WechatEnum;
import com.ty.wq.exception.WqException;
import com.ty.wq.pojo.vo.BaseReqVo;
import com.ty.wq.pojo.vo.Result;
import com.ty.wq.pojo.vo.client.wechatFriend.WechatFriendReqVo;
import com.ty.wq.pojo.vo.client.wechatFriend.WechatFriendRespVo;
import com.ty.wq.pojo.vo.client.wechatMessage.SendMsg;
import com.ty.wq.pojo.vo.netty.Option;
import com.ty.wq.service.client.WechatFriendService;
import com.ty.wq.utils.ReqVoUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        return wechatFriendService.delFriend(vo);
    }

    /**
     * 修改好友备注
     * @param vo
     * @return
     */
    @PostMapping("/updateRemark")
    public Result updateRemark(@RequestBody WechatFriendReqVo vo) {
        ReqVoUtils.validated(vo, BaseReqVo.Remark.class);
        return wechatFriendService.updateRemark(vo);
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
        wechatFriendService.toTop(vo);
        return Result.success();
    }

    /**
     * 免打扰
     * @param vo
     * @return
     */
    @PostMapping("/disturb")
    public Result disturb(@RequestBody WechatFriendReqVo vo) {
        ReqVoUtils.validated(vo, BaseReqVo.Disturb.class);
        wechatFriendService.toDisturb(vo);
        return Result.success();
    }

}

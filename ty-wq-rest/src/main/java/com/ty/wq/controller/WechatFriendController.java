package com.ty.wq.controller;

import com.ty.wq.pojo.vo.BaseReqVo;
import com.ty.wq.pojo.vo.Result;
import com.ty.wq.pojo.vo.client.wechatFriend.WechatFriendReqVo;
import com.ty.wq.pojo.vo.client.wechatFriend.WechatFriendRespVo;
import com.ty.wq.service.client.WechatFriendService;
import com.ty.wq.utils.ReqVoUtils;
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
     * 添加好友申请
     * @param vo
     * @return
     */
    @PostMapping("/apply")
    public Result newFriend(@RequestBody WechatFriendReqVo vo) {
        ReqVoUtils.validated(vo, BaseReqVo.Apply.class);
        wechatFriendService.newFriend(vo);
        return Result.success();
    }

    /**
     * 获取微信好友申请列表
     * @param wechatId 微信id
     * @return
     */
    @PostMapping("/apply/list/{wechatId}")
    public Result newFriends(@Valid @NotBlank(message = "微信id参数错误") @PathVariable String wechatId) {
        List<WechatFriendRespVo> vos = wechatFriendService.getNewFriends(wechatId);
        return Result.success(vos);
    }

    /**
     * 处理微信好友申请
     * @param vo
     * @return
     */
    @PostMapping("/handle")
    public Result handle(@RequestBody WechatFriendReqVo vo) {
        wechatFriendService.handleNewFriend(vo);
        return Result.success();
    }

    /**
     * 获取微信好友列表
     * @param wechatId 微信id
     * @return
     */
    @PostMapping("/list/{wechatId}")
    public Result friends(@Valid @NotBlank(message = "微信id参数错误") @PathVariable String wechatId) {
        List<WechatFriendRespVo> vos = wechatFriendService.getWechatFriends(wechatId);
        return Result.success(vos);
    }

}

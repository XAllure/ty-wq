package com.ty.wq.controller;

import com.ty.wq.pojo.vo.Result;
import com.ty.wq.pojo.vo.client.wechatFriend.WechatFriendRespVo;
import com.ty.wq.service.client.WechatFriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
     * 获取好友列表
     * @param wechatId
     * @return
     */
    @PostMapping("/list/{wechatId}")
    public Result friends(@Valid @NotBlank(message = "微信id参数错误") @PathVariable String wechatId) {
        List<WechatFriendRespVo> vos = wechatFriendService.getFriends(wechatId);
        return Result.success(vos);
    }

}

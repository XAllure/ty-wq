package com.ty.wq.controller;

import com.ty.wq.pojo.vo.Result;
import com.ty.wq.pojo.vo.client.weChat.WeChatRespVo;
import com.ty.wq.service.client.UserWeChatService;
import com.ty.wq.service.client.WeChatService;
import com.ty.wq.utils.AccessUtils;
import com.ty.wq.utils.OrikaUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("/user/weChat")
public class UserWeChatController {

    @Autowired
    private UserWeChatService userWeChatService;

    @Autowired
    private WeChatService weChatService;

    /**
     * 获取用户绑定的微信
     * @return
     */
    @PostMapping("/list")
    public Result userWeChats() {
        List<Long> weChatIds = userWeChatService.getWeChatIds(AccessUtils.userId());
        List<WeChatRespVo> weChats = OrikaUtils.converts(weChatService.findByIds(weChatIds), WeChatRespVo.class);
        return Result.success(weChats);
    }

}

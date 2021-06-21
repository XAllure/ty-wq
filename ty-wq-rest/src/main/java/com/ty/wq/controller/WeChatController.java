package com.ty.wq.controller;

import com.ty.wq.pojo.vo.BaseReqVo;
import com.ty.wq.pojo.vo.Result;
import com.ty.wq.pojo.vo.client.weChat.WeChatReqVo;
import com.ty.wq.pojo.vo.client.weChat.WeChatRespVo;
import com.ty.wq.service.client.WechatService;
import com.ty.wq.utils.OrikaUtils;
import com.ty.wq.utils.ReqVoUtils;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("/weChat")
@Slf4j
public class WeChatController {

    @Autowired
    private WechatService weChatService;

    /**
     * 登录微信
     * @param weChatReqVo
     * @return
     */
    @PostMapping("/login")
    public Result login(@RequestBody WeChatReqVo weChatReqVo) {
        ReqVoUtils.validated(weChatReqVo, BaseReqVo.Login.class);
        List<WeChatRespVo> weChats = OrikaUtils.converts(weChatService.login(weChatReqVo), WeChatRespVo.class);
        return Result.success(weChats);
    }

}

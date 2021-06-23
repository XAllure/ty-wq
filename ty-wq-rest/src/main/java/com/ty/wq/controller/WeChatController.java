package com.ty.wq.controller;

import com.ty.wq.pojo.vo.BaseReqVo;
import com.ty.wq.pojo.vo.Result;
import com.ty.wq.pojo.vo.client.wechat.WechatReqVo;
import com.ty.wq.pojo.vo.client.wechat.WechatRespVo;
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
    private WechatService wechatService;

    /**
     * 登录微信
     * @param wechatReqVo
     * @return
     */
    @PostMapping("/login")
    public Result login(@RequestBody WechatReqVo wechatReqVo) {
        ReqVoUtils.validated(wechatReqVo, BaseReqVo.Login.class);
        List<WechatRespVo> respVos = OrikaUtils.converts(wechatService.login(wechatReqVo), WechatRespVo.class);
        return Result.success(respVos);
    }

}

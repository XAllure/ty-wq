package com.ty.wq.controller;

import com.ty.wq.pojo.vo.BaseReqVo;
import com.ty.wq.pojo.vo.Result;
import com.ty.wq.pojo.vo.client.wechatMessage.WechatMessageReqVo;
import com.ty.wq.service.client.WechatMessageService;
import com.ty.wq.utils.ReqVoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("/wechat/message")
public class WechatMessageController {

    @Autowired
    private WechatMessageService wechatMessageService;

    @PostMapping("/send")
    public Result send(@RequestBody WechatMessageReqVo vo) {
        ReqVoUtils.validated(vo, BaseReqVo.Chat.class);
        wechatMessageService.send(vo);
        return Result.success();
    }

}

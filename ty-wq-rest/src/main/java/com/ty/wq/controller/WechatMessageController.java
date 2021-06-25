package com.ty.wq.controller;

import com.ty.wq.pojo.vo.Result;
import com.ty.wq.service.client.WechatMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
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

    /**
     * 获取消息列表
     * @return
     */
    @PostMapping("/list")
    public Result list() {
        return Result.success();
    }

}

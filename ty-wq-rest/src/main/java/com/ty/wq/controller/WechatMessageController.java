package com.ty.wq.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ty.wq.pojo.vo.BaseReqVo;
import com.ty.wq.pojo.vo.Result;
import com.ty.wq.pojo.vo.client.wechatMessage.WechatMessageReqVo;
import com.ty.wq.pojo.vo.client.wechatMessage.WechatMessageRespVo;
import com.ty.wq.pojo.vo.client.wechatMessage.WechatMessageSearchVo;
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


    /**
     * 获取历史消息
     * @param searchVo
     * @return
     */
    @PostMapping("/history")
    public Result history(WechatMessageSearchVo searchVo) {
        Page<WechatMessageRespVo> respVos = wechatMessageService.findPage(searchVo, WechatMessageRespVo.class);
        return Result.success(respVos);
    }

}

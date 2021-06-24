package com.ty.wq.controller;

import com.ty.wq.pojo.po.client.Wechat;
import com.ty.wq.pojo.vo.BaseReqVo;
import com.ty.wq.pojo.vo.Result;
import com.ty.wq.pojo.vo.client.wechat.WechatLoginReqVo;
import com.ty.wq.pojo.vo.client.wechat.WechatRespVo;
import com.ty.wq.service.client.WechatService;
import com.ty.wq.utils.OrikaUtils;
import com.ty.wq.utils.ReqVoUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("/wechat")
@Slf4j
public class WechatController {

    @Autowired
    private WechatService wechatService;

    /**
     * 登录微信
     * @param wechatLoginReqVo
     * @return
     */
    @PostMapping("/login")
    public Result login(@RequestBody WechatLoginReqVo wechatLoginReqVo) {
        ReqVoUtils.validated(wechatLoginReqVo, BaseReqVo.Login.class);
        List<WechatRespVo> respVos = wechatService.login(wechatLoginReqVo);
        return Result.success(respVos);
    }

    /**
     * 根据微信id获取个人信息
     * @param wechatId
     * @return
     */
    @PostMapping("/info/{wechatId}")
    public Result info(@Valid @NotBlank(message = "微信id参数错误") @PathVariable String wechatId) {
        WechatRespVo respVo = wechatService.getByWechatId(wechatId);
        return Result.success(respVo);
    }

}

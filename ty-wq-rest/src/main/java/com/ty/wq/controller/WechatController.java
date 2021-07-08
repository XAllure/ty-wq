package com.ty.wq.controller;

import com.ty.wq.pojo.vo.BaseReqVo;
import com.ty.wq.pojo.vo.Result;
import com.ty.wq.pojo.vo.client.wechat.WechatLoginReqVo;
import com.ty.wq.pojo.vo.client.wechat.WechatReqVo;
import com.ty.wq.pojo.vo.client.wechat.WechatRespVo;
import com.ty.wq.pojo.vo.netty.Wechat.NeWechatLoginReqVo;
import com.ty.wq.service.client.WechatService;
import com.ty.wq.utils.*;
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
        NeWechatLoginReqVo neWechatLoginReqVo = new NeWechatLoginReqVo();
        neWechatLoginReqVo.setUserId(AccessUtils.userId());
        neWechatLoginReqVo.setWechats(respVos);
        return RouteUtils.result(neWechatLoginReqVo, "/wechat/login");
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

    /**
     * 修改个人微信信息
     * @param vo
     * @return
     */
    @PostMapping("/update")
    public Result update(@RequestBody WechatReqVo vo) {
        ReqVoUtils.validated(vo, BaseReqVo.Update.class);
        wechatService.update(vo);
        return Result.success();
    }

}

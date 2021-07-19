package com.ty.wq.controller;

import com.ty.wq.pojo.vo.BaseReqVo;
import com.ty.wq.pojo.vo.Result;
import com.ty.wq.pojo.vo.netty.Wechat.NeWechatLoginReqVo;
import com.ty.wq.utils.ChannelUtils;
import com.ty.wq.utils.ReqVoUtils;
import io.netty.channel.Channel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("/wechat")
public class NeWechatController {

    /**
     * 注册微信的channel
     * @param reqVo
     * @return
     */
    @PostMapping("/login")
    public Result result(@RequestBody NeWechatLoginReqVo reqVo) {
        ReqVoUtils.validated(reqVo, BaseReqVo.Login.class);
        if (reqVo.getUserId() == null) {
            return Result.error("用户ID不能为空");
        }
        if (reqVo.getWechats() == null || reqVo.getWechats().isEmpty()) {
            return Result.error("请选择要登录的微信");
        }
        Channel channel = ChannelUtils.getUserChannel(reqVo.getUserId());
        ChannelUtils.setWeChatChannels(channel, reqVo.getWechats());
        return Result.success(reqVo.getWechats());
    }

}

package com.ty.wq.controller;

import com.ty.wq.enums.WechatEnum;
import com.ty.wq.pojo.po.client.Wechat;
import com.ty.wq.pojo.vo.BaseReqVo;
import com.ty.wq.pojo.vo.Result;
import com.ty.wq.pojo.vo.netty.Wechat.NeWechatReqVo;
import com.ty.wq.service.client.WechatService;
import com.ty.wq.utils.ChannelUtils;
import com.ty.wq.utils.ReqVoUtils;
import io.netty.channel.Channel;
import org.apache.commons.lang.StringUtils;
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
@RequestMapping("/wechat")
public class NeWechatController {

    @Autowired
    private WechatService wechatService;

    /**
     * 注册微信的channel
     * @param reqVo
     * @return
     */
    @PostMapping("/login")
    public Result result(@RequestBody NeWechatReqVo reqVo) {
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

    /**
     * 退出微信登录 删除channel
     * @param reqVo
     * @return
     */
    @PostMapping("/logout")
    public Result logout(@RequestBody NeWechatReqVo reqVo) {
        if (reqVo.getUserId() == null) {
            return Result.error("用户ID不能为空");
        }
        if (StringUtils.isBlank(reqVo.getWechatId())) {
            return Result.error("请选择要退出的微信");
        }
        Channel channel = ChannelUtils.getUserChannel(reqVo.getUserId());
        ChannelUtils.delByWechatIdAndChannel(reqVo.getWechatId(), channel);
        List<Channel> channels = ChannelUtils.getChannelsByWechatId(reqVo.getWechatId());
        if (channels == null || channels.size() == 0) {
            Wechat wechat = wechatService.findByWechatId(reqVo.getWechatId());
            wechat.setIsLogin(WechatEnum.LOGGED_OUT.getCode());
            wechat.setIsOnline(WechatEnum.OFFLINE.getCode());
            wechatService.updateById(wechat);
        }
        return Result.success();
    }

}

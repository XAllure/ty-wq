package com.ty.wq.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ty.wq.constant.ApiType;
import com.ty.wq.constant.OptionKey;
import com.ty.wq.pojo.po.client.WechatMessage;
import com.ty.wq.pojo.vo.BaseReqVo;
import com.ty.wq.pojo.vo.Result;
import com.ty.wq.pojo.vo.client.wechatMessage.*;
import com.ty.wq.pojo.vo.netty.Option;
import com.ty.wq.pojo.vo.netty.report.WechatMessageVo;
import com.ty.wq.service.client.WechatMessageService;
import com.ty.wq.utils.OrikaUtils;
import com.ty.wq.utils.ReqVoUtils;
import com.ty.wq.utils.RouteUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("/wechat/message")
@Slf4j
@Api(tags = "微信消息相关")
public class WechatMessageController {

    @Autowired
    private WechatMessageService wechatMessageService;

    @ApiOperation(value = "发送文本消息")
    @PostMapping("/sendTextMessage")
    public Result sendTextMessage(@RequestBody WechatMessageReqVo vo) {
        ReqVoUtils.validated(vo, BaseReqVo.Chat.class);
        SendMsg sMsg = new SendMsg();
        sMsg.setApi(ApiType.SEND_TEXT_MESSAGE);
        sMsg.setSendId(vo.getWechatId());
        sMsg.setOption(Option.option()
                .add(OptionKey.WXID, vo.getWxIdTo())
                .add(OptionKey.TEXT, vo.getContent())
                .getOption());
        return RouteUtils.send(sMsg);
    }

    @ApiOperation(value = "发送@文本消息,文本消息text的内容中设置占位符{$@},代表被@群成员，占位符的数量必须和atlist中的微信号数量相等")
    @PostMapping("/sendAtTextMessage")
    public Result sendAtTextMessage(@RequestBody WechatMessageReqVo vo) {
        ReqVoUtils.validated(vo, BaseReqVo.At.class);
        SendMsg sMsg = new SendMsg();
        sMsg.setApi(ApiType.SEND_AT_TEXT_MESSAGE);
        sMsg.setSendId(vo.getWechatId());
        sMsg.setOption(Option.option()
                .add(OptionKey.WXID, vo.getWxIdTo())
                .add(OptionKey.TEXT, vo.getContent())
                .add(OptionKey.AT_LIST, vo.getAtList())
                .getOption());
        return RouteUtils.send(sMsg);
    }

    @ApiOperation(value = "发送图片")
    @PostMapping("/sendPicMessage")
    public Result sendPicMessage(@RequestBody WechatMessageReqVo vo) {
        ReqVoUtils.validated(vo, BaseReqVo.Chat.class);
        SendMsg sMsg = new SendMsg();
        sMsg.setApi(ApiType.SEND_PIC_MESSAGE);
        sMsg.setSendId(vo.getWechatId());
        sMsg.setOption(Option.option()
                .add(OptionKey.WXID, vo.getWxIdTo())
                .add(OptionKey.IMG_PATH, vo.getContent())
                .getOption());
        return RouteUtils.send(sMsg);
    }

    @ApiOperation(value = "发送文件")
    @PostMapping("/sendFileMessage")
    public Result sendFileMessage(@RequestBody WechatMessageReqVo vo) {
        ReqVoUtils.validated(vo, BaseReqVo.Chat.class);
        SendMsg sMsg = new SendMsg();
        sMsg.setApi(ApiType.SEND_FILE_MESSAGE);
        sMsg.setSendId(vo.getWechatId());
        sMsg.setOption(Option.option()
                .add(OptionKey.WXID, vo.getWxIdTo())
                .add(OptionKey.FILE_PATH, vo.getContent())
                .getOption());
        return RouteUtils.send(sMsg);
    }

    @ApiOperation(value = "发送视频")
    @PostMapping("/sendVideoMessage")
    public Result sendVideoMessage(@RequestBody WechatMessageReqVo vo) {
        ReqVoUtils.validated(vo, BaseReqVo.Chat.class);
        SendMsg sMsg = new SendMsg();
        sMsg.setApi(ApiType.SEND_VIDEO_MESSAGE);
        sMsg.setSendId(vo.getWechatId());
        sMsg.setOption(Option.option()
                .add(OptionKey.WXID, vo.getWxIdTo())
                .add(OptionKey.VIDEO_PATH, vo.getContent())
                .getOption());
        return RouteUtils.send(sMsg);
    }

    @ApiOperation(value = "发送GIF表情")
    @PostMapping("/sendGifMessage")
    public Result sendGifMessage(@RequestBody WechatMessageReqVo vo) {
        ReqVoUtils.validated(vo, BaseReqVo.Chat.class);
        SendMsg sMsg = new SendMsg();
        sMsg.setApi(ApiType.SEND_GIF_MESSAGE);
        sMsg.setSendId(vo.getWechatId());
        sMsg.setOption(Option.option()
                .add(OptionKey.WXID, vo.getWxIdTo())
                .add(OptionKey.GIF_PATH, vo.getContent())
                .getOption());
        return RouteUtils.send(sMsg);
    }

    @ApiOperation(value = "发送链接消息")
    @PostMapping("/sendLinkMessage")
    public Result sendLinkMessage(@RequestBody LinkMessageReqVo vo) {
        ReqVoUtils.validated(vo, BaseReqVo.Chat.class);
        SendMsg sMsg = new SendMsg();
        sMsg.setApi(ApiType.SEND_LINK_MESSAGE);
        sMsg.setSendId(vo.getWechatId());
        sMsg.setOption(Option.option()
                .add(OptionKey.WXID, vo.getWxIdTo())
                .add(OptionKey.TITLE, vo.getTitle())
                .add(OptionKey.URL, vo.getUrl())
                .add(OptionKey.DESC, vo.getDesc())
                .add(OptionKey.PIC, vo.getPic())
                .getOption());
        return RouteUtils.send(sMsg);
    }

    @ApiOperation(value = "发送名片消息")
    @PostMapping("/sendCardMessage")
    public Result sendCardMessage(@RequestBody WechatMessageReqVo vo) {
        ReqVoUtils.validated(vo, BaseReqVo.Chat.class);
        SendMsg sMsg = new SendMsg();
        sMsg.setApi(ApiType.SEND_CARD_MESSAGE);
        sMsg.setSendId(vo.getWechatId());
        sMsg.setOption(Option.option()
                .add(OptionKey.WXID, vo.getWxIdTo())
                .add(OptionKey.WXID_CARD, vo.getContent())
                .getOption());
        return RouteUtils.send(sMsg);
    }

    @ApiOperation(value = "发送小程序")
    @PostMapping("/sendMiniMessage")
    public Result sendMiniMessage(@RequestBody WechatMessageReqVo vo) {
        ReqVoUtils.validated(vo, BaseReqVo.Chat.class);
        SendMsg sMsg = new SendMsg();
        sMsg.setApi(ApiType.SEND_MINI_MESSAGE);
        sMsg.setSendId(vo.getWechatId());
        sMsg.setOption(Option.option()
                .add(OptionKey.WXID, vo.getWxIdTo())
                .add(OptionKey.MSG_ID, vo.getContent())
                .getOption());
        return RouteUtils.send(sMsg);
    }

    @ApiOperation(value = "发送xml消息")
    @PostMapping("/sendXmlMessage")
    public Result sendXmlMessage(@RequestBody WechatMessageReqVo vo) {
        ReqVoUtils.validated(vo, BaseReqVo.Chat.class);
        SendMsg sMsg = new SendMsg();
        sMsg.setApi(ApiType.SEND_XML_MESSAGE);
        sMsg.setSendId(vo.getWechatId());
        sMsg.setOption(Option.option()
                .add(OptionKey.WXID, vo.getWxIdTo())
                .add(OptionKey.XML, vo.getContent())
                .getOption());
        return RouteUtils.send(sMsg);
    }

    @ApiOperation(value = "发送名片xml消息")
    @PostMapping("/sendCardXmlMessage")
    public Result sendCardXmlMessage(@RequestBody WechatMessageReqVo vo) {
        ReqVoUtils.validated(vo, BaseReqVo.Chat.class);
        SendMsg sMsg = new SendMsg();
        sMsg.setApi(ApiType.SEND_CARD_XML_MESSAGE);
        sMsg.setSendId(vo.getWechatId());
        sMsg.setOption(Option.option()
                .add(OptionKey.WXID, vo.getWxIdTo())
                .add(OptionKey.XML, vo.getContent())
                .getOption());
        return RouteUtils.send(sMsg);
    }

    @ApiOperation(value = "接收好友转账")
    @PostMapping("/acceptBankTransfer")
    public Result acceptBankTransfer(@RequestBody WechatMessageReqVo vo) {
        ReqVoUtils.validated(vo, BaseReqVo.Update.class);
        SendMsg sMsg = new SendMsg();
        sMsg.setApi(ApiType.ACCEPT_BANK_TRANSFER);
        sMsg.setSendId(vo.getWechatId());
        sMsg.setOption(Option.option()
                // 收哪一笔转账(收到转账xml信息 中的 transferid)
                .add(OptionKey.TRANSFER_ID, vo.getContent())
                .getOption());
        return RouteUtils.send(sMsg);
    }

    @ApiOperation(value = "退还好友转账")
    @PostMapping("/refuseFriendWcpay")
    public Result refuseFriendWcpay(@RequestBody WechatMessageReqVo vo) {
        ReqVoUtils.validated(vo, BaseReqVo.Update.class);
        SendMsg sMsg = new SendMsg();
        sMsg.setApi(ApiType.REFUSE_FRIEND_WCPAY);
        sMsg.setSendId(vo.getWechatId());
        sMsg.setOption(Option.option()
                // 退还哪一笔转账(收到转账xml信息 中的 transferid)
                .add(OptionKey.TRANSFER_ID, vo.getContent())
                .getOption());
        return RouteUtils.send(sMsg);
    }

    @ApiOperation(value = "分页获取消息记录")
    @PostMapping("/records")
    public Result records(@RequestBody WechatMessageSearchVo searchVo) {
        Page<WechatMessage> messages = wechatMessageService.getRecordMsg(searchVo);
        List<WechatMessageRespVo> respVos = new ArrayList<>();
        for (WechatMessage message : messages.getRecords()) {
            JSONObject data = JSON.parseObject(message.getContent());
            WechatMessageVo mVo = data.toJavaObject(WechatMessageVo.class);
            WechatMessageRespVo respVo = OrikaUtils.convert(message, WechatMessageRespVo.class);
            respVo.setContent(mVo.getMessage());
            respVo.setAtList(mVo.getAtlist());
            respVo.setImage(mVo.getImage());
            respVo.setImageThumb(mVo.getImagethumb());
            respVo.setXmlMsg(mVo.getXmlmsg());
            respVo.setFileIndex(mVo.getFileIndex());
            respVo.setCoverIndex(mVo.getCoverIndex());
            respVo.setVideoIndex(mVo.getVideoIndex());
            respVo.setVoiceIndex(mVo.getVoiceIndex());
            respVo.setMp3Index(mVo.getMp3Index());
            respVos.add(respVo);
        }
        Collections.reverse(respVos);
        Page<WechatMessageRespVo> respVoPage = OrikaUtils.pageNoRecords(messages);
        respVoPage.setRecords(respVos);
        return Result.success(respVoPage);
    }

}

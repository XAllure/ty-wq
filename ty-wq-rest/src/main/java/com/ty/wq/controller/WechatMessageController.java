package com.ty.wq.controller;

import com.ty.wq.constant.ApiType;
import com.ty.wq.constant.OptionKey;
import com.ty.wq.pojo.vo.BaseReqVo;
import com.ty.wq.pojo.vo.Result;
import com.ty.wq.pojo.vo.client.wechatMessage.*;
import com.ty.wq.pojo.vo.netty.Option;
import com.ty.wq.utils.ReqVoUtils;
import com.ty.wq.utils.RouteUtils;
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

    /**
     * 发送文本消息
     * @param vo
     * @return
     */
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

    /**
     * 发送@文本消息
     * 文本消息text的内容中设置占位符{$@},代表被@群成员，
     * 占位符的数量必须和atlist中的微信号数量相等
     * @param vo
     * @return
     */
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

    /**
     * 发送图片
     * @param vo
     * @return
     */
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

    /**
     * 发送文件
     * @param vo
     * @return
     */
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

    /**
     * 发送视频
     * @param vo
     * @return
     */
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

    /**
     * 发送GIF表情
     * @param vo
     * @return
     */
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

    /**
     * 发送链接消息
     * @param vo
     * @return
     */
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

    /**
     * 发送GIF表情
     * @param vo
     * @return
     */
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

    /**
     * 发送小程序
     * @param vo
     * @return
     */
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

    /**
     * 发送xml消息
     * @param vo
     * @return
     */
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

    /**
     * 发送名片xml消息
     * @param vo
     * @return
     */
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


    /**
     * 接收好友转账
     * @param vo
     * @return
     */
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

    /**
     * 退还好友转账
     * @param vo
     * @return
     */
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

}

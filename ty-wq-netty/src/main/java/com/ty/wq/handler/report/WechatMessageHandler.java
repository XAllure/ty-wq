package com.ty.wq.handler.report;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ty.wq.enums.WechatEnum;
import com.ty.wq.pojo.po.client.Wechat;
import com.ty.wq.pojo.po.client.WechatMessage;
import com.ty.wq.pojo.vo.client.wechatMessage.ReceiveMsg;
import com.ty.wq.pojo.vo.client.wechatMessage.WechatMessageRespVo;
import com.ty.wq.service.client.WechatMessageService;
import com.ty.wq.service.client.WechatService;
import com.ty.wq.utils.GenerateUtils;
import com.ty.wq.utils.OrikaUtils;
import com.ty.wq.utils.SendUtils;
import com.ty.wq.vo.WechatMessageVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 */
@Service
@Slf4j
public class WechatMessageHandler {

    @Autowired
    private WechatService wechatService;

    @Autowired
    private WechatMessageService wechatMessageService;

    /**
     * 上报文本消息
     * @param rMsg
     */
    @Async
    public void textMessageHandler(ReceiveMsg rMsg) {
        WechatMessageVo mVo = setWechatMessageVo(rMsg);
        WechatMessageRespVo respVo = setWechatMessageRespVo(rMsg, mVo);
        respVo.setContent(mVo.getMessage());
        //通知
        SendUtils.send(rMsg.getCwxid(), rMsg.getAction(), respVo);

    }

    /**
     * 上报图片消息
     * @param rMsg
     */
    @Async
    public void picMessageHandler(ReceiveMsg rMsg) {
        WechatMessageVo mVo = setWechatMessageVo(rMsg);
        WechatMessageRespVo respVo = setWechatMessageRespVo(rMsg, mVo);
        respVo.setContent("");
        respVo.setImage(mVo.getImage());
        respVo.setImageThumb(mVo.getImagethumb());
        respVo.setXmlMsg(mVo.getXmlmsg());
        log.info("图片消息: {}", respVo);
        //通知
        SendUtils.send(rMsg.getCwxid(), rMsg.getAction(), respVo);
    }

    /**
     * 转为 WechatMessageVo
     * @param rMsg
     * @return
     */
    private WechatMessageVo setWechatMessageVo(ReceiveMsg rMsg) {
        JSONObject data = JSON.parseObject(String.valueOf(rMsg.getData()));
        return data.toJavaObject(WechatMessageVo.class);
    }

    /**
     * 给 WechatMessage 赋值
     * @param rMsg
     * @param mVo
     */
    private WechatMessageRespVo setWechatMessageRespVo(ReceiveMsg rMsg, WechatMessageVo mVo) {
        WechatMessage message = new WechatMessage();
        Wechat wechat = wechatService.findByWechatId(rMsg.getCwxid());
        message.setWechatId(rMsg.getCwxid());
        message.setMsgType(mVo.getMsgType());
        message.setIsSend(mVo.getMyMsg());
        message.setIsPc(mVo.getIspc());
        message.setMsgId(mVo.getMsgid());
        message.setMsgSvrId(wechat.getId() + GenerateUtils.generateNumber(19));
        message.setRoomWxId(mVo.getRoomWxid());
        message.setWxIdFrom(mVo.getWxidFrom());
        message.setWxIdTo(mVo.getWxidTo());
        message.setContent(String.valueOf(rMsg.getData()));
        message.setCompanyId(wechat.getCompanyId());
        message.setDepartmentId(wechat.getDepartmentId());
        message.setTimestamp(mVo.getTimestamp());
        //插入
        wechatMessageService.insert(message);
        return OrikaUtils.convert(message, WechatMessageRespVo.class);
    }

}

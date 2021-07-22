package com.ty.wq.controller;

import com.ty.wq.constant.MsgType;
import com.ty.wq.enums.CodeEnum;
import com.ty.wq.pojo.vo.BaseReqVo;
import com.ty.wq.pojo.vo.Result;
import com.ty.wq.pojo.vo.client.wechatMessage.SendMsg;
import com.ty.wq.pojo.vo.netty.Message;
import com.ty.wq.pojo.vo.netty.MsgVo;
import com.ty.wq.utils.ChannelUtils;
import com.ty.wq.utils.MsgUtils;
import com.ty.wq.utils.ReqVoUtils;
import com.ty.wq.utils.WsTokenUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("/client")
@ApiIgnore
public class ClientController {

    @Value("${spring.profiles.active}")
    private String active;

    @ApiOperation(value = "往转发客户端发送数据")
    @PostMapping("/sendMsg/{token}")
    public Result addFriend(@RequestBody SendMsg sendMsg, @PathVariable String token) {
        System.out.println(active);
        if (!"dev".equals(active)) {
            if (!WsTokenUtils.hasToken(token)) {
                return Result.error(CodeEnum.ERROR_TOKEN);
            }
        }
        ReqVoUtils.validated(sendMsg, BaseReqVo.Chat.class);
        MsgVo msgVo = new MsgVo();
        msgVo.setType(MsgType.SEND_MSG);
        msgVo.setData(sendMsg);
        MsgUtils.writeJson(ChannelUtils.getClientChannel(sendMsg.getSendId()), Message.success(msgVo));
        return Result.success();
    }

}

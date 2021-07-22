package com.ty.wq.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ty.wq.constant.MsgType;
import com.ty.wq.pojo.vo.Result;
import com.ty.wq.pojo.vo.client.wechatMessage.SendMsg;
import com.ty.wq.pojo.vo.netty.Message;
import com.ty.wq.pojo.vo.netty.MsgVo;
import com.ty.wq.socket.WebSocketClient;
import com.ty.wq.utils.MsgUtils;
import com.ty.wq.utils.QueueUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

/**
 *
 * @author Administrator
 */
@RestController
@Slf4j
@ApiIgnore
public class ApiController {

    @ApiOperation(value = "轮询消息")
    @GetMapping("/send_msg")
    public Object friends() {
        LinkedList<SendMsg> list = new LinkedList<>();
        int size = QueueUtils.messages.size();
        for (int i = 0; i < size; i++) {
            list.add(QueueUtils.messages.poll());
        }
        return list;
    }

    @ApiOperation(value = "轮询消息回调")
    @PostMapping("/recieve_msg")
    public void receiveMsg(HttpServletRequest request) throws IOException {
        BufferedReader br = request.getReader();
        String str;
        StringBuilder wholeStr = new StringBuilder();
        while((str = br.readLine()) != null){
            wholeStr.append(str);
        }
        JSONObject data = JSON.parseObject(String.valueOf(wholeStr));
        MsgVo msgVo = new MsgVo();
        msgVo.setType(MsgType.RECEIVE_MSG);
        msgVo.setData(data.getJSONObject("data"));
        // log.info(String.valueOf(msgVo));
        MsgUtils.writeJson(WebSocketClient.channel, Message.success(msgVo));
    }

}

package com.ty.wq.scanner;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ty.wq.constant.MsgType;
import com.ty.wq.pojo.vo.netty.Message;
import com.ty.wq.pojo.vo.netty.MsgVo;
import com.ty.wq.socket.WebSocketClient;
import com.ty.wq.utils.HttpUtils;
import com.ty.wq.utils.MsgUtils;
import com.ty.wq.utils.OrikaUtils;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Scanner;

/**
 * @author Administrator
 */
@Service
@Slf4j
public class WsScanner implements Runnable {

    @Autowired
    private WebSocketClient webSocketClient;

    @Override
    public void run() {
        try(Scanner scanner = new Scanner(System.in)) {
            //noinspection InfiniteLoopStatement
            while (true) {
                System.out.println("请输入消息类型");
                String type = scanner.nextLine();
                /*if (StringUtils.isBlank(type)) {
                    log.info("请输入消息类型！");
                    continue;
                }*/
                System.out.println("请输入消息内容");
                String data = scanner.nextLine();
                System.out.println("请输入链接后缀");
                String url = scanner.nextLine();
                if (type.equals(MsgType.LOGIN)) {
                    webSocketClient.run();
                    log.info("客户端重新登录成功");
                    continue;
                }
                // 如果链接不为空 则发送http消息
                if (StringUtils.isNotBlank(url)) {
                    if (StringUtils.isBlank(data)) {
                        data = "empty:" + true;
                    }
                    data = "{" + data + "}";
                    // data 需要为空，或是格式为 username:"墨染",userNick:"红尘" 才行，这可以对应每一个Controller 的 @RequestBody 后的vo实体类， 单个字符加在链接后面
                    JSONObject json = JSON.parseObject(data);
                    String res = HttpUtils.post(WebSocketClient.URL.concat(url), json);
                    log.info("发送信息返回：" + res);
                }
                // 否则发送 websocket 消息
                else {
                    if (StringUtils.isBlank(type)) {
                        log.info("请输入消息类型！");
                        continue;
                    }
                    MsgVo msgVo = new MsgVo();
                    msgVo.setType(type);
                    msgVo.setToken(WebSocketClient.token);
                    msgVo.setData(data);
                    MsgUtils.writeJson(WebSocketClient.channel, Message.success(msgVo));
                }
            }
        } catch (Exception e) {
            log.info("出错了");
            log.info(e.getMessage());
        }
    }
}

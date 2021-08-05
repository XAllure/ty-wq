package com.ty.wq.scanner;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ty.wq.pojo.vo.netty.Message;
import com.ty.wq.pojo.vo.netty.MsgVo;
import com.ty.wq.socket.WebSocketClient;
import com.ty.wq.utils.HttpUtils;
import com.ty.wq.utils.MsgUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Scanner;

/**
 * @author Administrator
 */
@Service
@Slf4j
public class WsScanner implements Runnable {

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        //noinspection InfiniteLoopStatement
        while (true) {
            try {
                System.out.println("请输入消息内容");
                String data = scanner.nextLine();
                System.out.println("请输入链接后缀");
                String url = scanner.nextLine();
                if (StringUtils.isNotBlank(url)) {
                    data = "{" + data + "}";
                    // data 需要为空，或是格式为 username:"墨染",userNick:"红尘" 才行，这可以对应每一个Controller 的 @RequestBody 后的vo实体类
                    JSONObject json = JSON.parseObject(data);
                    String res = HttpUtils.post(WebSocketClient.URL.concat(url), WebSocketClient.token, json);
                    log.info("发送信息返回：" + res);
                }
            } catch (Exception e) {
                log.info("出错了");
                e.printStackTrace();
            }
        }
    }
}

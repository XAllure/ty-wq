package com.ty.wq.utils;

import com.alibaba.fastjson.JSONObject;
import com.ty.wq.constant.Constants;
import com.ty.wq.pojo.vo.netty.Message;
import com.ty.wq.pojo.vo.netty.WsServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/***
 * http代理
 * @author Administrator
 */
@Component
public class HttpUtils {

    @Autowired
    private static final RestTemplate REST_TEMPLATE = new RestTemplate();

    /**
     * get 请求
     * @param url
     * @param param
     * @return
     */
    public static String get(String url,Object... param){
        return REST_TEMPLATE.getForObject(url,String.class,param);
    }

    /**
     * 默认的post请求
     * @param url
     * @param params
     */
    public static void post(String url, Map<String, Object> params){
        post(url, Constants.CONTENT_TYPE_JSON, params);
    }

    public static String post(String url, Message message){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Message> request = new HttpEntity<>(message,headers);
        return REST_TEMPLATE.postForObject(url,request,String.class);
    }

    public static String post(String url, JSONObject jsonObject){
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        HttpEntity<String> request = new HttpEntity<>(jsonObject.toJSONString(),headers);
        return REST_TEMPLATE.postForObject(url,request,String.class);
    }


    /**
     * post请求
     * @param url
     * @param contentType application/json
     * @param params
     */
    public static void post(String url, String contentType, Map<String, Object> params){
        HttpHeaders headers = new HttpHeaders();
        if (Constants.CONTENT_TYPE_JSON.equalsIgnoreCase(contentType)){
            headers.setContentType(MediaType.APPLICATION_JSON);
        }
        HttpEntity<String> request = new HttpEntity<>(params.toString(),headers);
        REST_TEMPLATE.postForObject(url, request, String.class);
    }

    /**
     * 形成 url
     * @param ip IP地址
     * @param port 端口
     * @param suffix 链接后缀
     * @return 返回 url
     */
    public static String url(String ip, Integer port, String suffix) {
        return "http://" +ip + ":" + port + suffix;
    }

    /**
     * 形成 url
     * @param wsServer 服务器信息
     * @param suffix 链接后缀
     * @return 返回 url
     */
    public static String url(WsServer wsServer, String suffix) {
        return url(wsServer.getNIp(), wsServer.getHPort(), suffix);
    }

    /**
     * 通过 userId 获取该用户所在的服务器信息后形成 url
     * @param userId 用户id
     * @param suffix 链接后缀
     * @return 返回 url
     */
    public static String url(Long userId, String suffix) {
        // 通过 userId 获取该用户所在的服务器信息
        WsServer wsServer = WsTokenUtils.getUserWs(userId);
        return url(wsServer, suffix);
    }

    /**
     * 通过服务器id获取服务器信息后形成 url
     * @param serverId
     * @param suffix
     * @return
     */
    public static String url(String serverId, String suffix) {
        // 通过服务器id获取服务器信息
        WsServer wsServer = WsTokenUtils.getWsServer(serverId);
        return url(wsServer, suffix);
    }

}

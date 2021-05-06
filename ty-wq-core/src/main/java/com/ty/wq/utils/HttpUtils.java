package com.ty.wq.utils;

import com.alibaba.fastjson.JSONObject;
import com.ty.wq.constant.Constants;
import com.ty.wq.pojo.vo.netty.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
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
     * @return
     */
    public static String post(String url,Map<String, Object> params){
        return post(url, Constants.CONTENT_TYPE_JSON, params);
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
     * @return
     */
    public static String post(String url, String contentType, Map<String, Object> params){
        HttpHeaders headers = new HttpHeaders();
        if (Constants.CONTENT_TYPE_JSON.equalsIgnoreCase(contentType)){
            headers.setContentType(MediaType.APPLICATION_JSON);
        }
        HttpEntity<String> request = new HttpEntity<>(params.toString(),headers);
        return REST_TEMPLATE.postForObject(url,request,String.class);
    }
}

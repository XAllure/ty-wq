package com.ty.wq.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ty.wq.constant.Constants;
import com.ty.wq.enums.CodeEnum;
import com.ty.wq.exception.WqException;
import com.ty.wq.pojo.vo.Result;
import com.ty.wq.pojo.vo.netty.Route;
import com.ty.wq.pojo.vo.netty.WsServer;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Administrator
 */
public class RouteUtils {

    /** 高并发下使用 */
    private static final AtomicLong INDEX = new AtomicLong(0);

    /**
     * 形成 url
     * @param ip IP地址
     * @param port 端口
     * @param suffix 链接后缀
     * @return 返回 url
     */
    public static String url(String ip, Integer port, String suffix) {
        return "http://" + ip + ":" + port + suffix;
    }

    /**
     * 获取路由形成 url
     * @param suffix
     * @return
     */
    public static String url(String suffix) {
        // 获取路由信息(这里使用策略模式 1--轮询，2--推荐，3--随机，4--权重)
        long count = INDEX.incrementAndGet();
        Set<String> allKeys = RedisUtils.getAllKeys(Constants.WQ_ROUTE_INFO.concat("*"));
        if (allKeys.isEmpty()) {
            throw new WqException(CodeEnum.NOT_WS_SERVER.getCode(), "没有该路由");
        }
        List<String> keys = new ArrayList<>(allKeys);
        String key = keys.get((int) (count % keys.size()));
        // 获取服务器信息
        Route route = (Route) RedisUtils.get(key);
        return url(route.getIp(), route.getPort(), suffix);
    }

    /**
     * http发送往netty服务器
     * @param data
     * @param suffix
     * @return
     */
    public static Result send(Object data, String suffix) {
        if (StringUtils.isBlank(suffix)) {
            //默认发往netty服务器的链接
            suffix = "/client/sendMsg";
        }
        WsServer server = WsTokenUtils.getUserWs(AccessUtils.userId());
        JSONObject json = OrikaUtils.convert(data, JSONObject.class);
        String url = RouteUtils.url(server.getPip(), server.getHport(), suffix);
        String result = HttpUtils.post(url, json);
        JSONObject jsonObject = JSON.parseObject(result);
        return OrikaUtils.convert(jsonObject, Result.class);
    }

    /**
     * http发送往netty服务器
     * @param data
     * @return
     */
    public static Result send(Object data) {
        return send(data, null);
    }

}

package com.ty.wq.utils;

import com.ty.wq.constant.Constants;
import com.ty.wq.pojo.vo.netty.WsServer;
import jdk.nashorn.internal.parser.Token;

import java.util.concurrent.TimeUnit;


/**
 * token工具类
 * @author Administrator
 */
public class WsTokenUtils {

    public final static String WQ_TOKEN_HEADER = "TY-WQ-LOGIN-TOKEN";

    /**
     * 创建token
     * @return 返回token
     */
    public static String createToken(String salt){
        String token = GenerateUtils.generateString(32);
        token = Md5Utils.encryptSalt(token, salt);
        return token;
    }

    /**
     * 创建token
     * @return 返回token
     */
    public static String createToken(){
        return GenerateUtils.generateString(32);
    }

    /**
     * 存储token
     * @param token
     * @param userId
     * @param expire
     */
    public static void saveToken(String token, Long userId, long expire){
        RedisUtils.setValueHours(Constants.WQ_LOGIN_KEY.concat(token),userId,expire);
    }

    /**
     * 存储token
     * @param token
     * @param userId
     */
    public static void saveToken(String token, Long userId){
        saveToken(Constants.WQ_LOGIN_KEY.concat(token),userId,2);
    }

    /**
     * 存储token
     * @param token
     * @param userId
     */
    public static void saveAlwaysToken(String token, Long userId){
        RedisUtils.setValue(Constants.WQ_LOGIN_KEY.concat(token),userId);
    }

    /**
     * 校验token
     * @param token
     * @return
     */
    public static boolean validateToken(String token){
        return RedisUtils.hasKey(Constants.WQ_LOGIN_KEY.concat(token));
    }

    /**
     * 删除token
     * @param token
     */
    public static void deleteToken(String token) {
        RedisUtils.delete(Constants.WQ_LOGIN_KEY.concat(token));
    }

    /**
     * 更新token时效
     * @param token
     */
    public static void refreshToken(String token){
        RedisUtils.setExpire(Constants.WQ_LOGIN_KEY.concat(token), 2, TimeUnit.HOURS);
    }

    /**
     * 更新token时效
     * @param token
     */
    public static void refreshToken(String token, long timeout){
        RedisUtils.setExpire(Constants.WQ_LOGIN_KEY.concat(token), timeout, TimeUnit.HOURS);
    }

    /**
     * 保存用户服务器的信息
     * @param token
     * @param wsServer
     */
    public static void saveUserWs(String token, WsServer wsServer) {
        RedisUtils.setValue(Constants.WS_USER_SERVER.concat(token), wsServer.getId());
    }

    /**
     * 删除用户服务器的信息
     * @param token
     */
    public static void delUserWs(String token) {
        RedisUtils.delete(Constants.WS_USER_SERVER.concat(token));
    }

    /**
     * 校验用户服务器的信息
     * @param token
     * @return
     */
    public static boolean validateUserWs(String token){
        return RedisUtils.hasKey(Constants.WS_USER_SERVER.concat(token));
    }

    /**
     * 获取用户服务器的信息
     * @param token
     * @return
     */
    public static WsServer getUserWs(String token) {
        String id = (String) RedisUtils.getValue(Constants.WS_USER_SERVER.concat(token));
        return (WsServer) RedisUtils.getValue(Constants.WS_SERVER_INFO.concat(id));
    }

}

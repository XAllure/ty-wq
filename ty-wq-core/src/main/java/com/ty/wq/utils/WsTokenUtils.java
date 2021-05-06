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
    public static String createToken(){
        return GenerateUtils.generateString(32);
    }

    /**
     * 创建 token
     * @param salt 盐
     * @return 返回 token
     */
    public static String createToken(String salt){
        String token = GenerateUtils.generateString(32);
        token = Md5Utils.encryptSalt(token, salt);
        return token;
    }

    /**
     * 存储token
     * @param token
     * @param userId
     * @param expire
     */
    public static void saveToken(String token, Long userId, long expire){
        RedisUtils.setValueHours(Constants.WQ_LOGIN_KEY.concat(token), userId, expire);
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
        RedisUtils.setValue(Constants.WQ_LOGIN_KEY.concat(token), userId);
    }

    /**
     * 获取通过 token 存储的 userId
     * @param token
     * @return
     */
    public static Long getUserId(String token) {
        return Long.parseLong(String.valueOf(RedisUtils.getValue(Constants.WQ_LOGIN_KEY.concat(token))));
    }

    /**
     * 校验token
     * @param token
     * @return
     */
    public static boolean hasToken(String token){
        return RedisUtils.hasKey(Constants.WQ_LOGIN_KEY.concat(token));
    }

    /**
     * 删除token
     * @param token
     */
    public static void delToken(String token) {
        RedisUtils.delete(Constants.WQ_LOGIN_KEY.concat(token));
    }

    /**
     * 更新token时效
     * @param token
     */
    public static void refreshExpire(String token){
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
     * @param userId
     * @param wsServer
     */
    public static void saveUserWs(Long userId, WsServer wsServer) {
        RedisUtils.setValue(Constants.WS_USER_SERVER + userId, wsServer.getId());
    }

    /**
     * 删除用户服务器的信息
     * @param userId
     */
    public static void delUserWs(Long userId) {
        RedisUtils.delete(Constants.WS_USER_SERVER + userId);
    }

    /**
     * 校验用户服务器的信息
     * @param userId
     * @return
     */
    public static boolean hasUserWs(Long userId){
        return RedisUtils.hasKey(Constants.WS_USER_SERVER + userId);
    }

    /**
     * 获取用户服务器的信息
     * @param userId
     * @return
     */
    public static WsServer getUserWs(Long userId) {
        String id = (String) RedisUtils.getValue(Constants.WS_USER_SERVER + userId);
        return (WsServer) RedisUtils.getValue(Constants.WS_SERVER_INFO.concat(id));
    }

}

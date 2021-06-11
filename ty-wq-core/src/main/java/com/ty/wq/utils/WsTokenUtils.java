package com.ty.wq.utils;

import com.ty.wq.constant.Constants;
import com.ty.wq.pojo.vo.netty.WsServer;

import java.util.Objects;
import java.util.concurrent.TimeUnit;


/**
 * token工具类
 * @author Administrator
 */
public class WsTokenUtils {

    public final static String WQ_TOKEN_HEADER = "TY-WQ-LOGIN-TOKEN";

    /** token保存时间86400秒，即1440分钟，24小时 */
    private final static int TOKEN_EXPIRE = 86400;

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
        RedisUtils.setValueSeconds(Constants.WQ_LOGIN_KEY.concat(token), userId, expire);
    }

    /**
     * 存储token
     * @param token
     * @param userId
     */
    public static void saveToken(String token, Long userId){
        saveToken(token, userId, TOKEN_EXPIRE);
    }

    /**
     * 获取通过 token 存储的 userId
     * @param token
     * @return
     */
    public static Long getUserId(String token) {
        Object userId = RedisUtils.getValue(Constants.WQ_LOGIN_KEY.concat(token));
        if (Objects.isNull(userId)) {
            return null;
        }
        return Long.parseLong(String.valueOf(userId));
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
    public static void setExpire(String token){
        setExpire(Constants.WQ_LOGIN_KEY.concat(token), TOKEN_EXPIRE, TimeUnit.SECONDS);
        RedisUtils.setExpire(Constants.WS_USER_SERVER + getUserId(token), TOKEN_EXPIRE, TimeUnit.SECONDS);
    }

    /**
     * 更新token时效
     * @param token
     */
    public static void setExpire(String token, long timeout, TimeUnit timeUnit){
        RedisUtils.setExpire(Constants.WQ_LOGIN_KEY.concat(token), timeout, timeUnit);
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
        String id = String.valueOf(RedisUtils.getValue(Constants.WS_USER_SERVER + userId));
        return (WsServer) RedisUtils.getValue(Constants.WS_SERVER_INFO.concat(id));
    }

    /**
     * 通过服务器 id 获取服务器信息
     * @param serverId
     * @return
     */
    public static WsServer getWsServer(String serverId) {
        return (WsServer) RedisUtils.getValue(Constants.WS_SERVER_INFO.concat(serverId));
    }

}

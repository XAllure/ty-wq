package com.ty.wq.utils;

import com.ty.wq.constant.Constants;
import io.netty.channel.Channel;
import io.netty.util.AttributeKey;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Administrator
 */
public class ChannelUtils {

    /** 定义一个Map结构存储 userID 映射到 channel */
    private static final Map<Long, Channel> CHANNEL = new ConcurrentHashMap<>();

    /** 用户连接 netty 进行登录时 绑定的 token 属性 */
    public static final AttributeKey<String> WS_TOKEN = AttributeKey.valueOf("token");

    /**
     * 保存 Channel 对应的 token
     * 保存 userId 与 Channel 的对应关系
     * @param userId
     * @param channel
     */
    public static void save(Long userId, String token, Channel channel) {
        if (CHANNEL.containsKey(userId)) {
            Channel old = CHANNEL.get(userId);
            String oldToken = old.attr(WS_TOKEN).get();
            RedisUtils.delete(Constants.WQ_LOGIN_KEY.concat(oldToken));
            old.close();
        }
        channel.attr(ChannelUtils.WS_TOKEN).set(token);
        CHANNEL.put(userId, channel);
    }

    /**
     * 删除 userId 对应的 Channel
     * @param userId
     */
    public static void delete(Long userId) {
        CHANNEL.remove(userId);
    }

    /**
     * 获取 userId 对应的 Channel
     * @param userId
     * @return
     */
    public static Channel channel(Long userId) {
        return CHANNEL.get(userId);
    }

    /**
     * 获取所有的 Channel
     * @return
     */
    public static Collection<Channel> channels() {
        return CHANNEL.values();
    }

    /**
     * 通过 channel 获取 token
     * @param channel
     * @return
     */
    public static String getToken(Channel channel) {
        return channel.attr(WS_TOKEN).get();
    }

}

package com.ty.wq.utils;

import com.ty.wq.constant.Constants;
import com.ty.wq.pojo.po.client.Wechat;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Administrator
 */
@Slf4j
public class ChannelUtils {

    /** 用户连接 netty 进行登录时, channel 绑定的 token 属性 */
    public static final AttributeKey<String> WS_TOKEN = AttributeKey.valueOf("token");

    /** 用户连接 netty 进行登录时, channel 绑定的 useId 属性 */
    public static final AttributeKey<String> USER_ID = AttributeKey.valueOf("useId");

    /** 定义一个Map结构, 存储 userID 映射到 channel */
    public static final Map<Long, Channel> USER_ID_CHANNEL = new ConcurrentHashMap<>();

    /** 存储微信id 与 用户的 Channel 的映射，即同一个微信有几个用户登录，存储这些用户的 Channel*/
    public static final Map<String, List<Channel>> WE_CHAT_ID_CHANNEL = new ConcurrentHashMap<>();

    /**
     * 保存 Channel 对应的 token
     * 保存 Channel 对应的 userId
     * @param userId
     * @param channel
     */
    public static synchronized void saveUserChannel(Long userId, String token, Channel channel) {
        if (USER_ID_CHANNEL.containsKey(userId)) {
            Channel oldChannel = USER_ID_CHANNEL.get(userId);
            String oldToken = oldChannel.attr(WS_TOKEN).get();
            if (!token.equals(oldToken)) {
                RedisUtils.delete(Constants.WQ_LOGIN_KEY.concat(oldToken));
                oldChannel.close();
            } else {
                return;
            }
        }
        channel.attr(WS_TOKEN).set(token);
        channel.attr(USER_ID).set(String.valueOf(userId));
        USER_ID_CHANNEL.put(userId, channel);
    }

    /**
     * 删除 userId 对应的 Channel
     * @param channel
     */
    public static synchronized void delUserChannel(Channel channel) {
        Long userId = getUserId(channel);
        if (userId != null) {
            USER_ID_CHANNEL.remove(userId);
            log.info("删除 userId[{}] 对应的 Channel", userId);
        }
    }

    /**
     * 获取 userId 对应的 Channel
     * @param userId
     * @return
     */
    public static synchronized Channel userChannel(Long userId) {
        return USER_ID_CHANNEL.get(userId);
    }

    /**
     * 获取所有 userId 的 Channel
     * @return
     */
    public static synchronized Collection<Channel> allUserChannels() {
        return USER_ID_CHANNEL.values();
    }

    /**
     * 通过 channel 获取 token
     * @param channel
     * @return
     */
    public static synchronized String getToken(Channel channel) {
        return channel.attr(WS_TOKEN).get();
    }

    /**
     * 通过 Channel 获取 userId
     * @param channel
     * @return
     */
    public static synchronized Long getUserId(Channel channel) {
        String userId = channel.attr(USER_ID).get();
        if (StringUtils.isBlank(userId)) {
            return null;
        }
        return Long.valueOf(userId);
    }

    /**
     * 根据微信id存储所有用户的Channel通道
     * @param channel
     * @param wechats
     */
    public static synchronized void saveWeChatChannels(Channel channel, List<Wechat> wechats) {
        if (wechats != null && wechats.size() > 0) {
            List<Channel> channels;
            for (Wechat weChat : wechats) {
                String weChatId = weChat.getWechatId();
                if (StringUtils.isNotBlank(weChatId)) {
                    if (WE_CHAT_ID_CHANNEL.containsKey(weChatId)) {
                        channels = getChannelsByWeChatId(weChatId);
                    } else {
                        channels = new ArrayList<>();
                    }
                    channels.add(channel);
                    WE_CHAT_ID_CHANNEL.put(weChatId, channels);
                }
            }
        }
    }

    /**
     * 根据微信id获取所有用户的Channel通道
     * @param weChatId
     * @return
     */
    public static synchronized List<Channel> getChannelsByWeChatId(String weChatId) {
        List<Channel> channels = WE_CHAT_ID_CHANNEL.get(weChatId);
        if (channels != null && channels.size() > 0) {
            channels.removeIf(channel -> !channel.isActive() || !channel.isOpen());
        }
        return channels;
    }

    /**
     * 用户退出时调用，清理数据
     * @param ctx
     */
    public static synchronized void exit(ChannelHandlerContext ctx) {
        Channel channel = ctx.channel();
        delUserChannel(channel);
        ctx.close();
    }

}

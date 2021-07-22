package com.ty.wq.utils;

import com.ty.wq.constant.Constants;
import com.ty.wq.pojo.vo.client.wechat.WechatRespVo;
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
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Administrator
 */
@Slf4j
public class ChannelUtils {

    /** 用户连接 netty 进行登录时, channel 绑定的 token 属性 */
    public static final AttributeKey<String> USER_TOKEN = AttributeKey.valueOf("token");

    /** 用户连接 netty 进行登录时, channel 绑定的 userId 属性 */
    public static final AttributeKey<String> USER_ID = AttributeKey.valueOf("userId");

    /** 定义一个Map结构, 存储 userID 映射到 channel */
    public static final Map<Long, Channel> USER_ID_CHANNEL = new ConcurrentHashMap<>();

    /** 存储微信id 与 用户的 Channel 的映射，即同一个微信有几个用户登录，存储这些用户的 Channel*/
    public static final Map<String, List<Channel>> WECHAT_ID_CHANNEL = new ConcurrentHashMap<>();

    /** 存储微信id 与 转发客户端的映射 */
    public static final Map<String, List<Channel>> WECHAT_ID_CLIENT_CHANNEL = new ConcurrentHashMap<>();

    /**
     * 保存 Channel 对应的 token
     * 保存 Channel 对应的 userId
     * @param userId
     * @param channel
     */
    public static synchronized void setUserChannel(Long userId, String token, Channel channel) {
        if (USER_ID_CHANNEL.containsKey(userId)) {
            Channel oldChannel = USER_ID_CHANNEL.get(userId);
            String oldToken = oldChannel.attr(USER_TOKEN).get();
            if (!token.equals(oldToken)) {
                RedisUtils.delete(Constants.WQ_USER_LOGIN_KEY.concat(oldToken));
                oldChannel.close();
            } else {
                return;
            }
        }
        channel.attr(USER_TOKEN).set(token);
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
    public static synchronized Channel getUserChannel(Long userId) {
        return USER_ID_CHANNEL.get(userId);
    }

    /**
     * 获取所有 userId 的 Channel
     * @return
     */
    public static synchronized Collection<Channel> getAllUserChannels() {
        return USER_ID_CHANNEL.values();
    }

    /**
     * 通过 channel 获取 token
     * @param channel
     * @return
     */
    public static synchronized String getUserToken(Channel channel) {
        return channel.attr(USER_TOKEN).get();
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
     * 用户退出时调用，清理数据
     * @param ctx
     */
    public static synchronized void exit(ChannelHandlerContext ctx) {
        Channel channel = ctx.channel();
        delUserChannel(channel);
        ctx.close();
    }

    /**
     * 根据微信id存储所有用户的Channel通道
     * @param channel
     * @param wechats
     */
    public static synchronized void setWeChatChannels(Channel channel, List<WechatRespVo> wechats) {
        if (wechats != null && wechats.size() > 0) {
            List<Channel> channels;
            for (WechatRespVo wechat : wechats) {
                String wechatId = wechat.getWechatId();
                if (StringUtils.isNotBlank(wechatId)) {
                    if (WECHAT_ID_CHANNEL.containsKey(wechatId)) {
                        channels = getChannelsByWechatId(wechatId);
                    } else {
                        channels = new ArrayList<>();
                    }
                    channels.add(channel);
                    WECHAT_ID_CHANNEL.put(wechatId, channels);
                }
            }
        }
    }

    /**
     * 根据微信id获取所有用户的Channel通道
     * @param wechatId
     * @return
     */
    public static synchronized List<Channel> getChannelsByWechatId(String wechatId) {
        List<Channel> channels = WECHAT_ID_CHANNEL.get(wechatId);
        if (channels != null && channels.size() > 0) {
            channels.removeIf(channel -> !channel.isActive() || !channel.isOpen());
        }
        return channels;
    }

    /**
     * 退出微信登录删除该微信对应的channel
     * @param wechatId
     * @param channel
     */
    public static synchronized void delByWechatIdAndChannel(String wechatId, Channel channel) {
        List<Channel> channels = getChannelsByWechatId(wechatId);
        channels.removeIf(ch -> ch == channel);
        if (channels.size() > 0) {
            WECHAT_ID_CHANNEL.put(wechatId, channels);
        } else {
            WECHAT_ID_CHANNEL.remove(wechatId);
        }
    }

    /** 保存微信id和转发客户端的channel */
    public static synchronized void setWechatClientChannel(String wechatId, Channel channel) {
        List<Channel> channels;
        if (StringUtils.isNotBlank(wechatId)) {
            if (WECHAT_ID_CLIENT_CHANNEL.containsKey(wechatId)) {
                channels = getWechatClientChannels(wechatId);
            } else {
                channels = new ArrayList<>();
            }
            channels.add(channel);
            WECHAT_ID_CLIENT_CHANNEL.put(wechatId, channels);
        }
    }

    /** 获取微信id的转发客户端的channel */
    public static synchronized List<Channel> getWechatClientChannels(String wechatId) {
        List<Channel> channels = WECHAT_ID_CLIENT_CHANNEL.get(wechatId);
        if (channels != null && channels.size() > 0) {
            channels.removeIf(channel -> !channel.isActive() || !channel.isOpen());
        }
        return channels;
    }

    /** 高并发下使用 */
    private static final AtomicLong INDEX = new AtomicLong(0);

    /**
     * 暂时作为客户端转发的channel
     * @return
     */
    public static synchronized Channel getClientChannel(String wechatId) {
        long count = INDEX.incrementAndGet();
        List<Channel> channels = getWechatClientChannels(wechatId);
        int index = (int) (count % channels.size());
        return channels.get(index);
    }

    /**
     * 删除微信id对应的转发客户端的channel
     * @param wechatId
     * @param channel
     */
    public static synchronized void delClientByWechatIdAndChannel(String wechatId, Channel channel) {
        List<Channel> channels = getWechatClientChannels(wechatId);
        channels.removeIf(ch -> ch == channel);
        if (channels.size() > 0) {
            WECHAT_ID_CLIENT_CHANNEL.put(wechatId, channels);
        } else {
            WECHAT_ID_CLIENT_CHANNEL.remove(wechatId);
        }
    }

}

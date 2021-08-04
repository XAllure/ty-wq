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

/**
 * @author Administrator
 */
@Slf4j
public class ChannelUtils {

    /** 用户连接netty进行登录时,channel绑定的token属性 */
    public static final AttributeKey<String> USER_TOKEN = AttributeKey.valueOf("token");

    /** 用户连接netty进行登录时,channel绑定的userId属性 */
    public static final AttributeKey<String> USER_ID = AttributeKey.valueOf("userId");

    /** 定义一个Map结构,存储userID映射到channel */
    public static final Map<Long, Channel> USER_ID_CHANNEL = new ConcurrentHashMap<>();

    /** 存储微信id与用户的Channel的映射，即同一个微信有几个用户登录，存储这些用户的Channel*/
    public static final Map<String, List<Channel>> WECHAT_ID_CHANNEL = new ConcurrentHashMap<>();

    /** 询回调客户端连接netty进行登录时,channel绑定的srToken属性 */
    public static final AttributeKey<String> SR_TOKEN = AttributeKey.valueOf("srToken");

    /** 询回调客户端连接netty进行登录时,channel绑定的srId属性 */
    public static final AttributeKey<String> SR_ID = AttributeKey.valueOf("srId");

    /** 定义一个Map结构,存储srId映射到轮询回调客户端channel */
    public static final Map<Long, Channel> SR_ID_CHANNEL = new ConcurrentHashMap<>();

    /** 存储微信id与轮询回调客户端的映射 */
    public static final Map<String, Channel> WECHAT_ID_CLIENT_CHANNEL = new ConcurrentHashMap<>();

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
        if (getUserId(channel) != null) {
            delUserChannel(channel);
        } else if (getSrId(channel) != null) {
            delSrChannel(channel);
        }
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
        if (StringUtils.isNotBlank(wechatId)) {
            if (WECHAT_ID_CLIENT_CHANNEL.containsKey(wechatId)) {
                delClientByWechatId(wechatId);
            }
            WECHAT_ID_CLIENT_CHANNEL.put(wechatId, channel);
        }
    }

    /** 获取微信id的转发客户端的channel */
    public static synchronized Channel getWechatClientChannel(String wechatId) {
        return WECHAT_ID_CLIENT_CHANNEL.get(wechatId);
    }

    /**
     * 删除微信id对应的转发客户端的channel
     * @param wechatId
     */
    public static synchronized void delClientByWechatId(String wechatId) {
        WECHAT_ID_CLIENT_CHANNEL.remove(wechatId);
    }

    /**
     * 保存轮询客户端Channel 对应的 srToken
     * 保存轮询客户端Channel 对应的 srId
     * @param srId
     * @param channel
     */
    public static synchronized void setSrClientChannel(Long srId, String srToken, Channel channel) {
        if (SR_ID_CHANNEL.containsKey(srId)) {
            Channel oldChannel = SR_ID_CHANNEL.get(srId);
            String oldToken = oldChannel.attr(SR_TOKEN).get();
            if (!srToken.equals(oldToken)) {
                RedisUtils.delete(Constants.SR_LOGIN_KEY.concat(oldToken));
                oldChannel.close();
            } else {
                return;
            }
        }
        channel.attr(SR_TOKEN).set(srToken);
        channel.attr(SR_ID).set(String.valueOf(srId));
        SR_ID_CHANNEL.put(srId, channel);
    }

    /**
     * 通过轮询客户端channel 获取 srToken
     * @param channel
     * @return
     */
    public static synchronized String getSrToken(Channel channel) {
        return channel.attr(SR_TOKEN).get();
    }

    /**
     * 通过轮询客户端channel获取 srId
     * @param channel
     * @return
     */
    public static synchronized Long getSrId(Channel channel) {
        String srId = channel.attr(SR_ID).get();
        if (StringUtils.isBlank(srId)) {
            return null;
        }
        return Long.valueOf(srId);
    }

    /**
     * 删除 srId 对应的 Channel
     * @param channel
     */
    public static synchronized void delSrChannel(Channel channel) {
        Long srId = getSrId(channel);
        if (srId != null) {
            SR_ID_CHANNEL.remove(srId);
            log.info("删除 srId[{}] 对应的 Channel", srId);
        }
    }

}

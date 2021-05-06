package com.ty.wq.service.client.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ty.wq.constant.Constants;
import com.ty.wq.dao.client.UserDao;
import com.ty.wq.enums.ResultEnum;
import com.ty.wq.enums.UserStatusEnum;
import com.ty.wq.exception.WqException;
import com.ty.wq.pojo.po.client.User;
import com.ty.wq.pojo.vo.client.user.LoginReqVo;
import com.ty.wq.pojo.vo.client.user.LoginRespVo;
import com.ty.wq.pojo.vo.client.user.UserRespVo;
import com.ty.wq.pojo.vo.client.user.UserSearchVo;
import com.ty.wq.pojo.vo.netty.WsServer;
import com.ty.wq.service.client.UserService;
import com.ty.wq.service.base.impl.BaseServiceImpl;
import com.ty.wq.utils.Md5Utils;
import com.ty.wq.utils.OrikaUtils;
import com.ty.wq.utils.RedisUtils;
import com.ty.wq.utils.WsTokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Administrator
 * @version 1.0
 * @program: ty-wq
 * @description:
 * @date 2021-04-28 04:15:44
 */
@Service
@Slf4j
public class UserServiceImpl extends BaseServiceImpl<User, UserDao, UserSearchVo> implements UserService {

    /** 高并发下使用 */
    private final AtomicLong index = new AtomicLong(0);

    @Override
    public User findByUsername(String username) {
        QueryWrapper<User> qw = new QueryWrapper<>();
        //按用户名获取
        qw.eq("username",username);
        return this.findOne(qw);
    }

    @Override
    public LoginRespVo login(LoginReqVo vo) {
        User user = this.findByUsername(vo.getUsername());
        // 没有该账号
        if (user == null) {
            throw new WqException(ResultEnum.ERROR_ACCOUNT);
        }
        // 禁止登录
        if (user.getStatus().equals(UserStatusEnum.LOCKED.getCode())) {
            throw new WqException(ResultEnum.LOCKED_ACCOUNT);
        }
        // 密码不匹配
        if (user.getPassword().equals(Md5Utils.encryptSalt(vo.getPassword(), user.getSalt()))) {
            throw new WqException(ResultEnum.ERROR_PASSWORD);
        }
        // 获取 NettyWebSocket 服务器信息(这里使用策略模式 1--轮询，2--推荐，3--随机，4--权重)
        long count = index.incrementAndGet();
        Set<String> allKeys = RedisUtils.getAllKeys(Constants.WS_SERVER_INFO.concat("*"));
        if (allKeys.isEmpty()) {
            throw new WqException(ResultEnum.NOT_WS_SERVER);
        }
        List<String> keys = new ArrayList<>(allKeys);
        String key = keys.get((int) (count % keys.size()));
        // 获取服务器信息
        WsServer ws = (WsServer) RedisUtils.getValue(key);
        ws.setHPort(null);
        ws.setNIp(null);
        // 创建token
        String token = WsTokenUtils.createToken(user.getSalt());
        LoginRespVo respVo = new LoginRespVo();
        respVo.setToken(token);
        respVo.setUser(OrikaUtils.convert(user, UserRespVo.class));
        respVo.setServer(ws);
        // 保存用户登录的 token 与对应的 userId
        WsTokenUtils.saveAlwaysToken(token, user.getId());
        // 保存用户服务器的信息
        WsTokenUtils.saveUserWs(user.getId(), ws);
        log.info("为用户[{}]创建的token为: {}", user.getUsername(), token);
        log.info("为用户[{}]保存的服务器信息为: {}", user.getUsername(), WsTokenUtils.getUserWs(user.getId()));
        return respVo;
    }

}

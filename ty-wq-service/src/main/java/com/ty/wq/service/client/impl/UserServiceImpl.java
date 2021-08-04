package com.ty.wq.service.client.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ty.wq.constant.Constants;
import com.ty.wq.dao.client.UserDao;
import com.ty.wq.enums.CodeEnum;
import com.ty.wq.enums.StatusEnum;
import com.ty.wq.exception.WqException;
import com.ty.wq.pojo.po.client.Company;
import com.ty.wq.pojo.po.client.Department;
import com.ty.wq.pojo.po.client.User;
import com.ty.wq.pojo.vo.client.user.*;
import com.ty.wq.pojo.vo.netty.WsServer;
import com.ty.wq.service.client.CompanyService;
import com.ty.wq.service.client.DepartmentService;
import com.ty.wq.service.client.UserService;
import com.ty.wq.service.base.impl.BaseServiceImpl;
import com.ty.wq.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
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

    @Autowired
    private CompanyService companyService;

    @Autowired
    private DepartmentService departmentService;

    /** 高并发下使用 */
    private final AtomicLong index = new AtomicLong(0);

    @Override
    public UserRespVo getById(Long id) {
        User user = findById(id);
        UserRespVo vo = OrikaUtils.convert(user, UserRespVo.class);
        setCd(vo);
        return vo;
    }

    @Override
    public User findByUsername(String username) {
        QueryWrapper<User> qw = new QueryWrapper<>();
        //按用户名获取
        qw.eq("username",username);
        return this.findOne(qw);
    }

    /**
     * 轮询回调客户端登录
     * @param vo
     * @return
     */
    @Override
    public LoginRespVo srLogin(LoginReqVo vo) {
        // 先指定账号 后转数据库
        // 没有该账号
        if (!"sr001".equals(vo.getUsername())) {
            throw new WqException(CodeEnum.ERROR_ACCOUNT);
        }
        // 密码不匹配
        if (!vo.getPassword().equals(DigestUtils.md5Hex("123456"))) {
            throw new WqException(CodeEnum.ERROR_PASSWORD);
        }
        // 获取服务器信息
        WsServer ws = getServer();
        // 创建token
        String token = WsTokenUtils.createToken(GenerateUtils.generateString(18));
        LoginRespVo respVo = new LoginRespVo();
        respVo.setToken(token);
        respVo.setServer(ws);
        RedisUtils.setSeconds(Constants.SR_LOGIN_KEY.concat(token), 1L, Constants.TOKEN_EXPIRE);
        log.info("为轮询回调客户端[{}]保存的服务器信息为: {}", vo.getUsername(), RedisUtils.get(Constants.WQ_SERVER_INFO + 1));
        return respVo;
    }

    @Override
    public LoginRespVo login(LoginReqVo vo) {
        User user = this.findByUsername(vo.getUsername());
        // 没有该账号
        if (user == null) {
            throw new WqException(CodeEnum.ERROR_ACCOUNT);
        }
        // 禁止登录
        if (user.getStatus().equals(StatusEnum.LOCKED.getCode())) {
            throw new WqException(CodeEnum.LOCKED_ACCOUNT);
        }
        // 密码不匹配
        if (!user.getPassword().equals(Md5Utils.encryptSalt(vo.getPassword(), user.getSalt()))) {
            throw new WqException(CodeEnum.ERROR_PASSWORD);
        }
        user.setLoginTime(new Timestamp(System.currentTimeMillis()));
        // 获取服务器信息
        WsServer ws = getServer();
        // 创建token
        String token = WsTokenUtils.createToken(user.getSalt());
        LoginRespVo respVo = new LoginRespVo();
        respVo.setToken(token);
        respVo.setUser(OrikaUtils.convert(user, UserRespVo.class));
        respVo.setServer(ws);
        // 保存用户登录的 token 与对应的 userId
        WsTokenUtils.saveToken(token, user.getId());
        // 保存用户服务器的信息
        WsTokenUtils.saveUserWs(user.getId(), ws);
        log.info("为用户[{}]创建的token为: {}", user.getUsername(), token);
        log.info("为用户[{}]分配的服务器信息为: {}", user.getUsername(), ws);
        updateById(user);
        return respVo;
    }

    @Override
    public List<UserRespVo> toPageUsers(List<UserRespVo> respVos) {
        List<UserRespVo> vos = new ArrayList<>();
        for (UserRespVo vo : respVos) {
            setCd(vo);
            vos.add(vo);
        }
        return vos;
    }

    /**
     * 修改个人信息
     * @param vo
     */
    @Override
    public void updateSelf(UserReqVo vo) {
        User user = findById(AccessUtils.userId());
        user.setUsername(vo.getUsername());
        user.setUserNick(vo.getUserNick());
        user.setAvatar(vo.getAvatar());
        user.setUpdateBy(user.getUsername());
        updateById(user);
    }

    public WsServer getServer() {
        // 获取 NettyWebSocket 服务器信息(这里使用策略模式 1--轮询，2--推荐，3--随机，4--权重)
        long count = index.incrementAndGet();
        Set<String> allKeys = RedisUtils.getAllKeys(Constants.WQ_SERVER_INFO.concat("*"));
        if (allKeys.isEmpty()) {
            throw new WqException(CodeEnum.NOT_WS_SERVER);
        }
        List<String> keys = new ArrayList<>(allKeys);
        String key = keys.get((int) (count % keys.size()));
        // 获取服务器信息
        WsServer ws = (WsServer) RedisUtils.get(key);
        ws.setHport(null);
        ws.setNip(null);
        return ws;
    }

    public void setCd(UserRespVo vo) {
        Company company = companyService.findById(vo.getCompanyId());
        vo.setCompanyName(company.getName());
        Department department = departmentService.findById(vo.getDepartmentId());
        vo.setDepartmentName(department.getName());
    }

}

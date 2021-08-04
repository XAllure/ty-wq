package com.ty.wq.service.client;

import com.ty.wq.dao.client.UserDao;
import com.ty.wq.pojo.po.client.User;
import com.ty.wq.pojo.vo.client.user.*;
import com.ty.wq.service.base.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Administrator
 * @version 1.0
 * @program: ty-wq
 * @description:
 * @date 2021-04-28 04:15:44
 */
@Service
public interface UserService extends BaseService<User, UserDao, UserSearchVo> {

    /**
     * 按id获取
     * @param id
     * @return
     */
    UserRespVo getById(Long id);

    /**
     * 按用户名查询
     * @param username 用户名
     * @return User
     */
    User findByUsername(String username);

    /**
     * 轮询回调客户端登录
     * @param vo
     * @return
     */
    LoginRespVo srLogin(LoginReqVo vo);

    /**
     * 登录
     * @param vo
     * @return
     */
    LoginRespVo login(LoginReqVo vo);

    /**
     * 转化为分页的user数据
     * @param respVos
     * @return
     */
    List<UserRespVo> toPageUsers(List<UserRespVo> respVos);

    /**
     * 修改个人信息
     * @param vo
     */
    void updateSelf(UserReqVo vo);

}

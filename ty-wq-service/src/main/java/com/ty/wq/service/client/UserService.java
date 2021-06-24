package com.ty.wq.service.client;

import com.ty.wq.dao.client.UserDao;
import com.ty.wq.pojo.po.client.User;
import com.ty.wq.pojo.vo.client.user.LoginReqVo;
import com.ty.wq.pojo.vo.client.user.LoginRespVo;
import com.ty.wq.pojo.vo.client.user.UserRespVo;
import com.ty.wq.service.base.BaseService;
import com.ty.wq.pojo.vo.client.user.UserSearchVo;
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
     * 按用户名查询
     * @param username 用户名
     * @return User
     */
    User findByUsername(String username);

    /**
     * 登录
     * @param vo
     * @return
     */
    LoginRespVo login(LoginReqVo vo);

    /**
     * 转化为分页的user数据
     * @param userRespVos
     * @return
     */
    List<UserRespVo> toPageUsers(List<UserRespVo> userRespVos);

}

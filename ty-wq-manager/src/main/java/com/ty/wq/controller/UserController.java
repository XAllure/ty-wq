package com.ty.wq.controller;

import com.ty.wq.controller.base.BaseController;
import com.ty.wq.dao.client.UserDao;
import com.ty.wq.pojo.po.client.User;
import com.ty.wq.pojo.vo.client.user.UserReqVo;
import com.ty.wq.pojo.vo.client.user.UserRespVo;
import com.ty.wq.pojo.vo.client.user.UserSearchVo;
import com.ty.wq.service.client.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("/user")
public class UserController extends BaseController<User, UserReqVo, UserRespVo, UserSearchVo, UserDao, UserService> {
}

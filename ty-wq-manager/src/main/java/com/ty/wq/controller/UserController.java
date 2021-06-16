package com.ty.wq.controller;

import com.ty.wq.controller.base.BaseController;
import com.ty.wq.dao.client.UserDao;
import com.ty.wq.pojo.po.client.User;
import com.ty.wq.pojo.po.manager.Admin;
import com.ty.wq.pojo.vo.BaseReqVo;
import com.ty.wq.pojo.vo.Result;
import com.ty.wq.pojo.vo.client.user.UserReqVo;
import com.ty.wq.pojo.vo.client.user.UserRespVo;
import com.ty.wq.pojo.vo.client.user.UserSearchVo;
import com.ty.wq.pojo.vo.manager.admin.AdminReqVo;
import com.ty.wq.service.client.UserService;
import com.ty.wq.shiro.ShiroUtils;
import com.ty.wq.utils.GenerateUtils;
import com.ty.wq.utils.GoogleAuthenticatorUtils;
import com.ty.wq.utils.OrikaUtils;
import com.ty.wq.utils.ReqVoUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("/user")
public class UserController extends BaseController<User, UserReqVo, UserRespVo, UserSearchVo, UserDao, UserService> {

    UserController() {
        methodAdd = false;
    }

    /**
     * 添加
     * @param reqVo
     * @return
     */
    @PostMapping("/insert")
    public Result insert(@RequestBody UserReqVo reqVo) {
        ReqVoUtils.validated(reqVo, BaseReqVo.Add.class);
        User user = OrikaUtils.convert(reqVo, User.class);
        user.setSalt(GenerateUtils.generateString(20));
        user.setPassword(ShiroUtils.md5(user.getPassword(), user.getSalt()));
        service.insert(user);
        return Result.success();
    }

}

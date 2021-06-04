package com.ty.wq.controller;

import com.ty.wq.pojo.po.manager.Admin;
import com.ty.wq.pojo.vo.BaseReqVo;
import com.ty.wq.pojo.vo.Result;
import com.ty.wq.pojo.vo.manager.admin.LoginReqVo;
import com.ty.wq.pojo.vo.manager.admin.LoginRespVo;
import com.ty.wq.shiro.ShiroVerifyUser;
import com.ty.wq.utils.ReqVoUtils;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("/system")
public class SystemController {

    @PostMapping("/login")
    @ApiOperation(value = "管理员登录")
    public Result login(@RequestBody LoginReqVo loginReqVo){
        ReqVoUtils.validated(loginReqVo, BaseReqVo.Login.class);
        Subject subject = SecurityUtils.getSubject();
        //登录验证
        subject.login(new ShiroVerifyUser(loginReqVo));
        //生成sessionId
        String sessionId = String.valueOf(subject.getSession().getId());
        //成功返回 sessionId(token)
        Admin admin = (Admin) subject.getPrincipal();
        LoginRespVo loginResVo = new LoginRespVo(sessionId,admin.getAvatar(), admin.getUsername());
        return Result.success(loginResVo);
    }

}

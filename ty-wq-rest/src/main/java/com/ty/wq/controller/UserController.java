package com.ty.wq.controller;

import com.ty.wq.enums.CodeEnum;
import com.ty.wq.pojo.po.client.User;
import com.ty.wq.pojo.vo.BaseReqVo;
import com.ty.wq.pojo.vo.Result;
import com.ty.wq.pojo.vo.client.user.PasswordReqVo;
import com.ty.wq.pojo.vo.client.user.PhoneReqVo;
import com.ty.wq.pojo.vo.client.user.UserReqVo;
import com.ty.wq.pojo.vo.client.user.UserRespVo;
import com.ty.wq.service.client.UserService;
import com.ty.wq.utils.AccessUtils;
import com.ty.wq.utils.Md5Utils;
import com.ty.wq.utils.ReqVoUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("/user")
@Api(tags = "用户相关")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "按id获取用户信息")
    @PostMapping("/info/{id}")
    public Result info(@PathVariable Long id) {
        return Result.success(userService.getById(id));
    }

    @ApiOperation(value = "获取个人信息")
    @PostMapping("/info")
    public Result info() {
        UserRespVo userRespVo = userService.getById(AccessUtils.userId());
        return Result.success(userRespVo);
    }

    @ApiOperation(value = "修改个人信息")
    @PostMapping("/update")
    public Result update(@RequestBody UserReqVo userReqVo) {
        ReqVoUtils.validated(userReqVo, BaseReqVo.Self.class);
        userService.updateSelf(userReqVo);
        return Result.success();
    }

    @ApiOperation(value = "修改密码")
    @PostMapping("/password/update")
    public Result updatePassword(@RequestBody PasswordReqVo reqVo) {
        ReqVoUtils.validated(reqVo, BaseReqVo.Self.class);
        // 两次密码不一致
        if (!reqVo.getPassword().equals(reqVo.getConfirmPassword())) {
            return Result.error(CodeEnum.PASSWORD_2_NOT_SAME);
        }
        User user = userService.findById(AccessUtils.userId());
        reqVo.setPassword(Md5Utils.encryptSalt(reqVo.getPassword(), user.getSalt()));
        // 新旧密码一致
        if (reqVo.getPassword().equals(user.getPassword())) {
            return Result.error(CodeEnum.PASSWORD_SAME);
        }
        user.setPassword(reqVo.getPassword());
        userService.updateById(user);
        return Result.success();
    }

    @ApiOperation(value = "修改手机号")
    @PostMapping("/phone/update")
    public Result updatePhone(@RequestBody PhoneReqVo vo) {
        // 从redis获取手机号缓存的验证码

        // 验证验证码的一致性，不一致返回提示

        // 一致则更新
        User user = userService.findById(AccessUtils.userId());
        user.setPhone(vo.getPhone());
        // userService.updateById(user);
        return Result.success();
    }

}

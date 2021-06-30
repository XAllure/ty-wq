package com.ty.wq.controller;

import com.ty.wq.pojo.vo.Result;
import com.ty.wq.pojo.vo.client.user.UserReqVo;
import com.ty.wq.service.client.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 按id获取信息
     * @param id
     * @return
     */
    @PostMapping("/info/{id}")
    public Result info(@PathVariable Long id) {
        return Result.success(userService.getById(id));
    }

}

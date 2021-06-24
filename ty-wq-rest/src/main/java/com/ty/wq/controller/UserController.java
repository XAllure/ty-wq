package com.ty.wq.controller;

import com.ty.wq.service.client.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

}

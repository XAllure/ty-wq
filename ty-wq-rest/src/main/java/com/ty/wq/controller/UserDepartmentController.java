package com.ty.wq.controller;

import com.ty.wq.service.client.UserDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("/user/department")
public class UserDepartmentController {

    @Autowired
    private UserDepartmentService userDepartmentService;

}

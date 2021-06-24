package com.ty.wq.controller;

import com.ty.wq.service.client.DepartmentPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("/department/permission")
public class DepartmentPermissionController {

    @Autowired
    private DepartmentPermissionService departmentPermissionService;

}

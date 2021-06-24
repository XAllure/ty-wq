package com.ty.wq.controller;

import com.ty.wq.service.client.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("/permission")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

}

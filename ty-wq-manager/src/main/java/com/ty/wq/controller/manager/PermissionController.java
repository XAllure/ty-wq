package com.ty.wq.controller.manager;

import com.ty.wq.controller.BaseController;
import com.ty.wq.dao.client.PermissionDao;
import com.ty.wq.pojo.po.client.Permission;
import com.ty.wq.pojo.vo.Result;
import com.ty.wq.pojo.vo.client.permission.PermissionReqVo;
import com.ty.wq.pojo.vo.client.permission.PermissionRespVo;
import com.ty.wq.pojo.vo.client.permission.PermissionSearchVo;
import com.ty.wq.service.client.PermissionService;
import com.ty.wq.utils.OrikaUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("/permission")
public class PermissionController extends BaseController<Permission, PermissionReqVo, PermissionRespVo, PermissionSearchVo, PermissionDao, PermissionService> {

    /**
     * 获取所有权限
     * @return
     */
    @Override
    @GetMapping("/all")
    public Result all() {
        List<Permission> permissions = service.allPermission();
        List<PermissionRespVo> respVos = OrikaUtils.converts(permissions, PermissionRespVo.class);
        return Result.success(respVos);
    }

    /**
     * 获取父权限
     * @return
     */
    @PostMapping("/parent")
    public Result parent() {
        List<Permission> parentAuth = service.parentPermission();
        List<PermissionRespVo> respVos = OrikaUtils.converts(parentAuth, PermissionRespVo.class);
        return Result.success(respVos);
    }

}

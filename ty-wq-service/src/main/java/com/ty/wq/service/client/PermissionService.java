package com.ty.wq.service.client;

import com.ty.wq.dao.client.PermissionDao;
import com.ty.wq.pojo.po.client.Permission;
import com.ty.wq.service.base.BaseService;
import com.ty.wq.pojo.vo.client.permission.PermissionSearchVo;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author Administrator
 * @version 1.0
 * @program: ty-wq
 * @description:
 * @date 2021-06-17 08:15:02
 */
@Service
public interface PermissionService extends BaseService<Permission, PermissionDao, PermissionSearchVo> {

    /**
     * 查询所有权限
     * @return
     */
    List<Permission> allPermission();

    /**
     * 查询父权限
     * @return
     */
    List<Permission> parentAuthority();

}

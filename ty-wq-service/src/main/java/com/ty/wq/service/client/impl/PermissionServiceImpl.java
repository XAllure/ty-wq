package com.ty.wq.service.client.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ty.wq.dao.client.PermissionDao;
import com.ty.wq.enums.StatusEnum;
import com.ty.wq.pojo.po.client.Permission;
import com.ty.wq.pojo.vo.client.permission.PermissionSearchVo;
import com.ty.wq.service.client.PermissionService;
import com.ty.wq.service.base.impl.BaseServiceImpl;
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
public class PermissionServiceImpl extends BaseServiceImpl<Permission, PermissionDao, PermissionSearchVo> implements PermissionService {

    @Override
    public List<Permission> allPermission() {
        QueryWrapper<Permission> qw = new QueryWrapper<>();
        qw.eq("status", StatusEnum.NORMAL.getCode()).orderByAsc("sort");
        return findList(qw);
    }

    @Override
    public List<Permission> parentPermission() {
        QueryWrapper<Permission> qw = new QueryWrapper<>();
        qw.ne("status", StatusEnum.LOCKED.getCode())
                .isNull("permission")
                .or().eq("permission", "");
        return findList(qw);
    }

    /**
     * 根据id集合获取子权限列表
     * @param ids
     * @return
     */
    @Override
    public List<Permission> findByIds(List<Long> ids) {
        QueryWrapper<Permission> qw = new QueryWrapper<>();
        qw.in("id", ids)
                .isNotNull("permission")
                .ne("permission", "")
                .eq("status", StatusEnum.NORMAL.getCode());
        return findList(qw);
    }

}

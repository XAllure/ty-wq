package com.ty.wq.service.client.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ty.wq.dao.client.DepartmentPermissionDao;
import com.ty.wq.enums.StatusEnum;
import com.ty.wq.pojo.vo.client.departmentPermission.DepartmentPermissionReqVo;
import com.ty.wq.pojo.vo.client.departmentPermission.DepartmentPermissionSearchVo;
import com.ty.wq.pojo.po.client.DepartmentPermission;
import com.ty.wq.service.client.DepartmentPermissionService;
import com.ty.wq.service.base.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * @version 1.0
 * @program: ty-wq
 * @description:
 * @date 2021-06-21 03:41:49
 */
@Service
public class DepartmentPermissionServiceImpl extends BaseServiceImpl<DepartmentPermission, DepartmentPermissionDao,
        DepartmentPermissionSearchVo> implements DepartmentPermissionService {

    /**
     * 根据部门id获取权限id集合
     *
     * @param departmentId
     * @return
     */
    @Override
    public List<Long> getPermIdsByDepartmentId(Long departmentId) {
        QueryWrapper<DepartmentPermission> qw = new QueryWrapper<>();
        qw.eq("department_id", departmentId);
        List<DepartmentPermission> departmentPermissions = findList(qw);
        List<Long> permIds = new ArrayList<>();
        for (DepartmentPermission dp : departmentPermissions) {
            permIds.add(dp.getPermissionId());
        }
        return permIds;
    }

    /**
     * 更改部门权限
     * @param vo
     */
    @Override
    public void updateDepartmentPerms(DepartmentPermissionReqVo vo) {
        QueryWrapper<DepartmentPermission> qw = new QueryWrapper<>();
        qw.eq("department_id", vo.getDepartmentId());
        delete(qw);
        if (vo.getPermissionIds() != null && vo.getPermissionIds().size() >0 && !vo.getPermissionIds().isEmpty()) {
            List<DepartmentPermission> dps = new ArrayList<>();
            for (Long permId : vo.getPermissionIds()) {
                DepartmentPermission dp = new DepartmentPermission();
                dp.setDepartmentId(vo.getDepartmentId());
                dp.setPermissionId(permId);
                dps.add(dp);
            }
            inserts(dps);
        }
    }

    /**
     * 根据部门id列表查询部门权限列表
     * @param departmentIds
     * @return
     */
    @Override
    public List<DepartmentPermission> findByDepartmentIds(List<Long> departmentIds) {
        QueryWrapper<DepartmentPermission> qw = new QueryWrapper<>();
        qw.in("department_id", departmentIds).eq("status", StatusEnum.NORMAL.getCode());
        return findList(qw);
    }

}

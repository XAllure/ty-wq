package com.ty.wq.service.client.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ty.wq.dao.client.DepartmentPermissionDao;
import com.ty.wq.enums.StatusEnum;
import com.ty.wq.pojo.vo.client.departmentPermission.DepartmentPermissionSearchVo;
import com.ty.wq.pojo.po.client.DepartmentPermission;
import com.ty.wq.service.client.DepartmentPermissionService;
import com.ty.wq.service.base.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

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

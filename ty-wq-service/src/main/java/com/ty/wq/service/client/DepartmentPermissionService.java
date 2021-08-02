package com.ty.wq.service.client;

import com.ty.wq.dao.client.DepartmentPermissionDao;
import com.ty.wq.pojo.po.client.DepartmentPermission;
import com.ty.wq.service.base.BaseService;
import com.ty.wq.pojo.vo.client.departmentPermission.DepartmentPermissionSearchVo;
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
public interface DepartmentPermissionService extends BaseService<DepartmentPermission, DepartmentPermissionDao, DepartmentPermissionSearchVo> {

    /**
     * 根据部门id列表查询部门权限列表
     * @param departmentIds
     * @return
     */
    List<DepartmentPermission> findByDepartmentIds(List<Long> departmentIds);

}

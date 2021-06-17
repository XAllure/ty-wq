package com.ty.wq.service.manager.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ty.wq.dao.manager.AdminRoleDao;
import com.ty.wq.enums.StatusEnum;
import com.ty.wq.pojo.po.manager.Role;
import com.ty.wq.pojo.vo.manager.adminRole.AdminRoleReqVo;
import com.ty.wq.pojo.vo.manager.adminRole.AdminRoleSearchVo;
import com.ty.wq.pojo.po.manager.AdminRole;
import com.ty.wq.service.manager.AdminRoleService;
import com.ty.wq.service.base.impl.BaseServiceImpl;
import com.ty.wq.service.manager.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * @version 1.0
 * @program: ty-wq
 * @description:
 * @date 2021-06-07 03:50:41
 */
@Service
public class AdminRoleServiceImpl extends BaseServiceImpl<AdminRole, AdminRoleDao, AdminRoleSearchVo> implements AdminRoleService {

    @Autowired
    private RoleService roleService;

    @Override
    public List<Long> getRoleIdsByAdminId(Long adminId) {
        QueryWrapper<AdminRole> qw = new QueryWrapper<>();
        qw.eq("admin_id", adminId);
        List<AdminRole> adminRoles = findList(qw);
        List<Long> adminRoleIds = new ArrayList<>();
        for (AdminRole adminRole : adminRoles) {
            adminRoleIds.add(adminRole.getRoleId());
        }
        // 排除被禁用的角色
        QueryWrapper<Role> roleQw = new QueryWrapper<>();
        roleQw.in("id", adminRoleIds).ne("status", StatusEnum.LOCKED.getCode());
        List<Role> roles = roleService.findList(roleQw);
        adminRoleIds.clear();
        for (Role role : roles) {
            adminRoleIds.add(role.getId());
        }
        return adminRoleIds;
    }

    @Override
    public void updateAdminRoles(AdminRoleReqVo reqVo) {
        QueryWrapper<AdminRole> qw = new QueryWrapper<>();
        qw.eq("admin_id", reqVo.getAdminId());
        delete(qw);
        if (!reqVo.getRoleIds().isEmpty() && reqVo.getRoleIds().size() > 0) {
            List<AdminRole> adminRoles = new ArrayList<>();
            for (Long roleId : reqVo.getRoleIds()) {
                AdminRole adminRole = new AdminRole();
                adminRole.setAdminId(reqVo.getAdminId());
                adminRole.setRoleId(roleId);
                adminRole.setStatus(StatusEnum.NORMAL.getCode());
                adminRole.setCreateTime(new Timestamp(System.currentTimeMillis()));
                adminRole.setDeleted(0);
                adminRole.setVersion(0);
                adminRoles.add(adminRole);
            }
            inserts(adminRoles);
        }
    }
}

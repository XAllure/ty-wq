package com.ty.wq.service.manager.impl;
import java.sql.Timestamp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ty.wq.dao.manager.RoleMenuDao;
import com.ty.wq.enums.StatusEnum;
import com.ty.wq.pojo.vo.manager.roleMenu.RoleMenuReqVo;
import com.ty.wq.pojo.vo.manager.roleMenu.RoleMenuSearchVo;
import com.ty.wq.pojo.po.manager.RoleMenu;
import com.ty.wq.service.manager.RoleMenuService;
import com.ty.wq.service.base.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * @version 1.0
 * @program: ty-wq
 * @description:
 * @date 2021-06-07 05:48:54
 */
@Service
public class RoleMenuServiceImpl extends BaseServiceImpl<RoleMenu, RoleMenuDao, RoleMenuSearchVo> implements RoleMenuService {
    @Override
    public List<Long> getMenuIdByRoleId(Long roleId) {
        QueryWrapper<RoleMenu> qw = new QueryWrapper<>();
        qw.eq("role_id", roleId);
        // 按角色id查找
        List<RoleMenu> roleMenus = findList(qw);
        List<Long> roleMenuIds = new ArrayList<>();
        for (RoleMenu roleMenu : roleMenus) {
            roleMenuIds.add(roleMenu.getMenuId());
        }
        return roleMenuIds;
    }

    @Override
    public void updateRoleMenus(RoleMenuReqVo reqVo) {
        QueryWrapper<RoleMenu> qw = new QueryWrapper<>();
        qw.eq("role_id", reqVo.getRoleId());
        delete(qw);
        if (!reqVo.getMenuIds().isEmpty() && reqVo.getMenuIds().size() > 0) {
            List<RoleMenu> roleMenus = new ArrayList<>();
            for (Long menuId : reqVo.getMenuIds()) {
                RoleMenu roleMenu = new RoleMenu();
                roleMenu.setRoleId(reqVo.getRoleId());
                roleMenu.setMenuId(menuId);
                roleMenu.setStatus(StatusEnum.NORMAL.getCode());
                roleMenu.setCreateTime(new Timestamp(System.currentTimeMillis()));
                roleMenu.setDeleted(0);
                roleMenu.setVersion(0);
                roleMenus.add(roleMenu);
            }
            inserts(roleMenus);
        }
    }
}

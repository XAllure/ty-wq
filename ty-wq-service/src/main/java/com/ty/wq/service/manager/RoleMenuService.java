package com.ty.wq.service.manager;

import com.ty.wq.dao.manager.RoleMenuDao;
import com.ty.wq.pojo.po.manager.RoleMenu;
import com.ty.wq.pojo.vo.manager.menu.MenuRespVo;
import com.ty.wq.pojo.vo.manager.roleMenu.RoleMenuReqVo;
import com.ty.wq.service.base.BaseService;
import com.ty.wq.pojo.vo.manager.roleMenu.RoleMenuSearchVo;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author Administrator
 * @version 1.0
 * @program: ty-wq
 * @description:
 * @date 2021-06-07 05:48:54
 */
@Service
public interface RoleMenuService extends BaseService<RoleMenu, RoleMenuDao, RoleMenuSearchVo> {

    /**
     * 根据角色id获取菜单id
     * @param roleId 角色id
     * @return
     */
    List<Long> getMenuIdByRoleId(Long roleId);

    /**
     * 更新角色菜单
     * @param reqVo
     */
    void updateRoleMenus(RoleMenuReqVo reqVo);

    /**
     * 获取管理员所有角色的菜单
     * @return
     */
    List<MenuRespVo> getAdminRolesMenu(Long adminId);

}

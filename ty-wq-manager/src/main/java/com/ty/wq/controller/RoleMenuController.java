package com.ty.wq.controller;

import com.ty.wq.enums.StatusEnum;
import com.ty.wq.pojo.po.manager.Menu;
import com.ty.wq.pojo.vo.BaseReqVo;
import com.ty.wq.pojo.vo.Result;
import com.ty.wq.pojo.vo.manager.menu.MenuRespVo;
import com.ty.wq.service.manager.AdminRoleService;
import com.ty.wq.service.manager.MenuService;
import com.ty.wq.service.manager.RoleMenuService;
import com.ty.wq.shiro.ShiroUtils;
import com.ty.wq.utils.AccessUtils;
import com.ty.wq.utils.OrikaUtils;
import com.ty.wq.utils.ReqVoUtils;
import io.swagger.annotations.ApiOperation;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ty.wq.pojo.po.manager.RoleMenu;
import com.ty.wq.pojo.vo.manager.roleMenu.RoleMenuReqVo;
import com.ty.wq.pojo.vo.manager.roleMenu.RoleMenuRespVo;
import com.ty.wq.pojo.vo.manager.roleMenu.RoleMenuSearchVo;
import com.ty.wq.dao.manager.RoleMenuDao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * @author Administrator
 * @version 1.0
 * @program: ty-wq
 * @description:
 * @date 2021-06-07 05:48:54
 */
@RestController
@RequestMapping("/role")
public class RoleMenuController {

    @Autowired
    private RoleMenuService roleMenuService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private AdminRoleService adminRoleService;

    /**
     * 获取角色的菜单
     * @param roleId
     * @return
     */
    @PostMapping("/menu/{roleId}")
    public Result menuIds(@NonNull @PathVariable Long roleId){
        List<MenuRespVo> menuRespVos = menuService.allMenu();
        List<Long> menuIds = roleMenuService.getMenuIdByRoleId(roleId);
        RoleMenuRespVo roleMenuRespVo = new RoleMenuRespVo();
        roleMenuRespVo.setMenus(menuRespVos);
        roleMenuRespVo.setMenuIds(menuIds);
        return Result.success(roleMenuRespVo);
    }

    /**
     * 修改角色菜单
     * @param reqVo
     * @return
     */
    @PostMapping("/menu/update")
    @ApiOperation(value = "修改角色菜单", notes = "修改角色菜单")
    public Result update(@RequestBody RoleMenuReqVo reqVo){
        ReqVoUtils.validated(reqVo, BaseReqVo.Add.class);
        roleMenuService.updateRoleMenus(reqVo);
        return Result.success();
    }

    /**
     * 获取管理员所有角色的菜单
     * @return
     */
    @PostMapping("/menus")
    public Result menus() {
        List<Long> roleIds = adminRoleService.getRoleIdsByAdminId(ShiroUtils.getAdminId());
        List<Long> menuIds = new ArrayList<>();
        for (Long roleId : roleIds) {
            menuIds.addAll(roleMenuService.getMenuIdByRoleId(roleId));
        }
        //去掉重复的菜单id
        HashSet<Long> ids = new HashSet<>(menuIds);
        menuIds.clear();
        menuIds.addAll(ids);
        List<MenuRespVo> menuRespVos = OrikaUtils.converts(menuService.findBatchIds(menuIds), MenuRespVo.class);
        menuRespVos.removeIf(menuRespVo -> menuRespVo.getStatus().equals(StatusEnum.LOCKED.getCode()));
        return Result.success(menuRespVos);
    }

}

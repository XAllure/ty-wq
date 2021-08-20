package com.ty.wq.controller.manager;

import com.ty.wq.anno.RePermission;
import com.ty.wq.pojo.vo.BaseReqVo;
import com.ty.wq.pojo.vo.Result;
import com.ty.wq.pojo.vo.manager.menu.MenuRespVo;
import com.ty.wq.service.manager.MenuService;
import com.ty.wq.service.manager.RoleMenuService;
import com.ty.wq.shiro.ShiroUtils;
import com.ty.wq.utils.ReqVoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ty.wq.pojo.vo.manager.roleMenu.RoleMenuReqVo;
import com.ty.wq.pojo.vo.manager.roleMenu.RoleMenuRespVo;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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
@RePermission(prefix = "role")
public class RoleMenuController {

    @Autowired
    private RoleMenuService roleMenuService;

    @Autowired
    private MenuService menuService;

    /**
     * 获取角色的菜单
     * @param roleId
     * @return
     */
    @PostMapping("/menu/{roleId}")
    @RePermission("menu")
    public Result menuIds(@Valid @NotNull(message = "角色ID不能为空") @PathVariable Long roleId){
        List<MenuRespVo> menuRespVos = menuService.allNormalMenu();
        List<Long> menuIds = roleMenuService.getMenuIdsByRoleId(roleId);
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
    @RePermission("menu:update")
    public Result update(@RequestBody RoleMenuReqVo reqVo){
        ReqVoUtils.validated(reqVo, BaseReqVo.Update.class);
        roleMenuService.updateRoleMenus(reqVo);
        return Result.success();
    }

    /**
     * 获取管理员所有角色的菜单
     * @return
     */
    @PostMapping("/menus")
    @RePermission("menus")
    public Result menus() {
        List<MenuRespVo> menuRespVos = roleMenuService.getAdminRolesMenu(ShiroUtils.getAdminId());
        return Result.success(menuRespVos);
    }

}

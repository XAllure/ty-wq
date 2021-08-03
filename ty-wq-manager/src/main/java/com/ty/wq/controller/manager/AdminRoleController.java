package com.ty.wq.controller.manager;

import com.ty.wq.pojo.vo.BaseReqVo;
import com.ty.wq.pojo.vo.Result;
import com.ty.wq.pojo.vo.manager.role.RoleRespVo;
import com.ty.wq.service.manager.AdminRoleService;
import com.ty.wq.service.manager.RoleService;
import com.ty.wq.utils.OrikaUtils;
import com.ty.wq.utils.ReqVoUtils;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ty.wq.pojo.vo.manager.adminRole.AdminRoleReqVo;
import com.ty.wq.pojo.vo.manager.adminRole.AdminRoleRespVo;

import java.util.List;

/**
 * @author Administrator
 * @version 1.0
 * @program: ty-wq
 * @description:
 * @date 2021-06-07 03:50:41
 */
@RestController
@RequestMapping("/admin")
public class AdminRoleController {

    @Autowired
    private AdminRoleService adminRoleService;

    @Autowired
    private RoleService roleService;

    /**
     * 获取管理员角色
     * @param adminId
     * @return
     */
    @PostMapping("/roles/{adminId}")
    public Result adminRoles(@NonNull @PathVariable Long adminId){
        List<RoleRespVo> roleRespVos = OrikaUtils.converts(roleService.getAllNormalRole(), RoleRespVo.class);
        List<Long> roleIds = adminRoleService.getRoleIdsByAdminId(adminId);
        AdminRoleRespVo adminRoleRespVo = new AdminRoleRespVo(roleRespVos, roleIds);
        return Result.success(adminRoleRespVo);
    }

    /**
     * 修改管理员角色
     * @param adminRoleReqVo
     * @return
     */
    @PostMapping("/role/update")
    public Result update(@RequestBody AdminRoleReqVo adminRoleReqVo){
        ReqVoUtils.validated(adminRoleReqVo, BaseReqVo.Add.class);
        adminRoleService.updateAdminRoles(adminRoleReqVo);
        return Result.success();
    }

}

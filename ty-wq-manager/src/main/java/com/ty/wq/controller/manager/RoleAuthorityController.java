package com.ty.wq.controller.manager;

import com.ty.wq.anno.RePermission;
import com.ty.wq.controller.BaseController;
import com.ty.wq.pojo.po.manager.Admin;
import com.ty.wq.pojo.vo.BaseReqVo;
import com.ty.wq.pojo.vo.Result;
import com.ty.wq.pojo.vo.manager.authority.AuthorityRespVo;
import com.ty.wq.service.manager.AdminRoleService;
import com.ty.wq.service.manager.AdminService;
import com.ty.wq.service.manager.AuthorityService;
import com.ty.wq.service.manager.RoleAuthorityService;
import com.ty.wq.shiro.ShiroRealm;
import com.ty.wq.shiro.ShiroUtils;
import com.ty.wq.utils.OrikaUtils;
import com.ty.wq.utils.ReqVoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ty.wq.pojo.po.manager.RoleAuthority;
import com.ty.wq.pojo.vo.manager.roleAuthority.RoleAuthorityReqVo;
import com.ty.wq.pojo.vo.manager.roleAuthority.RoleAuthorityRespVo;
import com.ty.wq.pojo.vo.manager.roleAuthority.RoleAuthoritySearchVo;
import com.ty.wq.dao.manager.RoleAuthorityDao;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Administrator
 * @version 1.0
 * @program: ty-wq
 * @description:
 * @date 2021-08-17 05:15:34
 */
@RestController
@RequestMapping("/role")
@RePermission(prefix = "role")
public class RoleAuthorityController {

    @Autowired
    private RoleAuthorityService roleAuthorityService;

    @Autowired
    private AdminRoleService adminRoleService;

    @Autowired
    private AuthorityService authorityService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private ShiroRealm shiroRealm;

    /**
     * 根据角色ID获取权限ID集合
     * @param roleId
     * @return
     */
    @PostMapping("/authority/authorityIds/{roleId}")
    @RePermission("authority:authorityIds")
    public Result authorityIds(@Valid @NotNull(message = "角色ID不能为空") @PathVariable Long roleId) {
        List<AuthorityRespVo> authorityRespVos = OrikaUtils.converts(authorityService.getAll(), AuthorityRespVo.class);
        List<Long> authorityIds = roleAuthorityService.findAuthIdsByRoleId(roleId);
        RoleAuthorityRespVo respVo = new RoleAuthorityRespVo();
        respVo.setAuthorityIds(authorityIds);
        respVo.setAuthorities(authorityRespVos);
        return Result.success(respVo);
    }

    /**
     * 修改角色权限
     * @param reqVo
     * @return
     */
    @PostMapping("/authority/update")
    @RePermission("authority:update")
    public Result update(@RequestBody RoleAuthorityReqVo reqVo) {
        ReqVoUtils.validated(reqVo, BaseReqVo.Add.class);
        roleAuthorityService.updateRoleAuth(reqVo);
        // 重新缓存管理员角色权限
        List<Long> adminIds = adminRoleService.getAdminIdsByRoleId(reqVo.getRoleId());
        for (Admin admin : adminService.findBatchIds(adminIds)) {
            if (admin != null) {
                ShiroUtils.reloadAuthorizing(shiroRealm, admin);
            }
        }
        return Result.success();
    }

}

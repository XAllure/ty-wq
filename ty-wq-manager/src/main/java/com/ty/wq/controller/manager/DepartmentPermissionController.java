package com.ty.wq.controller.manager;

import com.ty.wq.anno.RePermission;
import com.ty.wq.pojo.vo.BaseReqVo;
import com.ty.wq.pojo.vo.Result;
import com.ty.wq.pojo.vo.client.departmentPermission.DepartmentPermissionReqVo;
import com.ty.wq.pojo.vo.client.departmentPermission.DepartmentPermissionRespVo;
import com.ty.wq.pojo.vo.client.permission.PermissionRespVo;
import com.ty.wq.service.client.DepartmentPermissionService;
import com.ty.wq.service.client.PermissionService;
import com.ty.wq.utils.OrikaUtils;
import com.ty.wq.utils.ReqVoUtils;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("/department/permission")
@RePermission(prefix = "department:permission")
public class DepartmentPermissionController {

    @Autowired
    private DepartmentPermissionService departmentPermissionService;

    @Autowired
    private PermissionService permissionService;

    /**
     * 根据部门id获取部门的权限
     * @param departmentId
     * @return
     */
    @PostMapping("/ids/{departmentId}")
    @RePermission("ids")
    public Result getPermissionIds(@NonNull @PathVariable Long departmentId) {
        List<PermissionRespVo> permissions = OrikaUtils.converts(permissionService.allPermission(), PermissionRespVo.class);
        List<Long> permissionIds = departmentPermissionService.getPermIdsByDepartmentId(departmentId);
        DepartmentPermissionRespVo respVo = new DepartmentPermissionRespVo();
        respVo.setPermissions(permissions);
        respVo.setPermissionIds(permissionIds);
        return Result.success(respVo);
    }

    /**
     * 更改部门权限
     * @param vo
     * @return
     */
    @PostMapping("/update")
    @RePermission("update")
    public Result update(@RequestBody DepartmentPermissionReqVo vo) {
        ReqVoUtils.validated(vo, BaseReqVo.Update.class);
        departmentPermissionService.updateDepartmentPerms(vo);
        return Result.success();
    }

}

package com.ty.wq.pojo.vo.client.departmentPermission;

import com.ty.wq.pojo.vo.BaseRespVo;
import com.ty.wq.pojo.vo.client.permission.PermissionRespVo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.List;

/**
 * @author Administrator
 * @version 1.0
 * @program: ty-wq
 * @description:
 * @date 2021-06-21 03:41:49
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DepartmentPermissionRespVo extends BaseRespVo {

    private static final long serialVersionUID = -1L;

    List<PermissionRespVo> permissions;

    List<Long> permissionIds;

}

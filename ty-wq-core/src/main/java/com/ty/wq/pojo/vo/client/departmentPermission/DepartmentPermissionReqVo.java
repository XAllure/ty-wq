package com.ty.wq.pojo.vo.client.departmentPermission;

import com.ty.wq.pojo.vo.BaseReqVo;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author Administrator
 * @version 1.0
 * @program: ty-wq
 * @description:
 * @date 2021-06-21 03:41:49
 */
@Data
public class DepartmentPermissionReqVo implements Serializable {

    private static final long serialVersionUID = -1L;

    @NotNull(message = "部门ID不能为空",groups = BaseReqVo.Update.class)
    private Long departmentId;

    private List<Long> permissionIds;

}

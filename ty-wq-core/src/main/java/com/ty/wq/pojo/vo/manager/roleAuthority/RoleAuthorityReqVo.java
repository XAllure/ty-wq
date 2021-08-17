package com.ty.wq.pojo.vo.manager.roleAuthority;

import com.ty.wq.pojo.vo.BaseReqVo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Administrator
 * @version 1.0
 * @program: ty-wq
 * @description:
 * @date 2021-08-17 05:15:34
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RoleAuthorityReqVo extends BaseReqVo {
    private static final long serialVersionUID = -1L;

    @NotNull(message = "角色ID不能为空",groups = Add.class)
    private Long roleId;

    /** 权限id列表 */
    private List<Long> authorityIds;

}

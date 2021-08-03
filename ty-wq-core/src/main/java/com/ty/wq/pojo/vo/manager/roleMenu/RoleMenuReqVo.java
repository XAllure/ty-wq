package com.ty.wq.pojo.vo.manager.roleMenu;

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
 * @date 2021-06-07 05:48:54
 */
@Data
public class RoleMenuReqVo implements Serializable {
    private static final long serialVersionUID = -1L;

    @NotNull(message = "角色ID不能为空",groups = BaseReqVo.Update.class)
    private Long roleId;

    private List<Long> menuIds;

}

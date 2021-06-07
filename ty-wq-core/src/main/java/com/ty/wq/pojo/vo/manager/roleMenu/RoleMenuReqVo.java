package com.ty.wq.pojo.vo.manager.roleMenu;

import com.ty.wq.pojo.vo.BaseReqVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.List;

/**
 * @author Administrator
 * @version 1.0
 * @program: ty-wq
 * @description:
 * @date 2021-06-07 05:48:54
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RoleMenuReqVo extends BaseReqVo {
    private static final long serialVersionUID = -1L;

    @NotNull(message = "角色ID不能为空",groups = Add.class)
    private Long roleId;

    @ApiModelProperty("menuIds")
    private List<Long> menuIds;

}

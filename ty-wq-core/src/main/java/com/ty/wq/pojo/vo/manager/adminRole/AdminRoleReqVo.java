package com.ty.wq.pojo.vo.manager.adminRole;

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
 * @date 2021-06-07 03:50:41
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AdminRoleReqVo extends BaseReqVo {
    private static final long serialVersionUID = -1L;

    @NotNull(message = "管理员id不能为空",groups = Add.class)
    private Long adminId;

    @ApiModelProperty("roleIds")
    private List<Long> roleIds;

}

package com.ty.wq.pojo.vo.manager.adminRole;

import com.ty.wq.pojo.vo.BaseRespVo;
import com.ty.wq.pojo.vo.manager.role.RoleRespVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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
@AllArgsConstructor
public class AdminRoleRespVo extends BaseRespVo {

    private static final long serialVersionUID = -1L;

    List<RoleRespVo> roles;

    List<Long> roleIds;

}

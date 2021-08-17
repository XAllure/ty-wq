package com.ty.wq.pojo.vo.manager.roleAuthority;

import com.ty.wq.pojo.vo.BaseRespVo;
import com.ty.wq.pojo.vo.manager.authority.AuthorityRespVo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.sql.Timestamp;
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
public class RoleAuthorityRespVo extends BaseRespVo {

    private static final long serialVersionUID = -1L;

    List<AuthorityRespVo> authorities;

    List<Long> authorityIds;

}

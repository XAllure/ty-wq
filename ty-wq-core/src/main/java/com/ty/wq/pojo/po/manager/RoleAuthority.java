package com.ty.wq.pojo.po.manager;

import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.IsNotNull;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import com.ty.wq.pojo.po.BasePo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色权限
 * @author Administrator
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Table("role_authority")
public class RoleAuthority extends BasePo {

    private static final long serialVersionUID = 4444687179412982614L;

    @IsNotNull
    @Column(type = MySqlTypeConstant.BIGINT, comment = "角色ID")
    private Long roleId;

    @IsNotNull
    @Column(type = MySqlTypeConstant.BIGINT, comment = "权限ID")
    private Long authorityId;

}

package com.ty.wq.pojo.po.manager;

import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import com.ty.wq.pojo.po.BasePo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Administrator
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Table("role_menu")
public class RoleMenu extends BasePo {
    private static final long serialVersionUID = -6854851378232251632L;

    @Column(type = MySqlTypeConstant.BIGINT, comment = "角色id")
    private Long roleId;

    @Column(type = MySqlTypeConstant.BIGINT, comment = "菜单id")
    private Long menuId;

}

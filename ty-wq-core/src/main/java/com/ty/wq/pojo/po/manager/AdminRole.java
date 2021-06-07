package com.ty.wq.pojo.po.manager;

import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.IsNotNull;
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
@Table("admin_role")
public class AdminRole extends BasePo {

    private static final long serialVersionUID = -5820141548229339933L;

    @IsNotNull
    @Column(type = MySqlTypeConstant.BIGINT, comment = "管理员id")
    private Long adminId;

    @IsNotNull
    @Column(type = MySqlTypeConstant.BIGINT, comment = "角色id")
    private Long roleId;

}

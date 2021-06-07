package com.ty.wq.pojo.po.manager;

import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.IsNotNull;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.annotation.Unique;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import com.ty.wq.pojo.po.BasePo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Administrator
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Table("role")
public class Role extends BasePo {
    private static final long serialVersionUID = -4129981176670634179L;

    @Unique
    @IsNotNull
    @Column(type = MySqlTypeConstant.VARCHAR, length = 30, comment = "角色名称")
    private String name;

    @Column(type = MySqlTypeConstant.VARCHAR, comment = "角色介绍")
    private String introduce;

}

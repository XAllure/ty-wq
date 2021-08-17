package com.ty.wq.pojo.po.manager;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.IsNotNull;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.annotation.Unique;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import com.ty.wq.pojo.po.BasePo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 权限
 * @author Administrator
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Table("authority")
public class Authority extends BasePo {

    private static final long serialVersionUID = -1035451497747903595L;

    @Unique
    @IsNotNull
    @Column(type = MySqlTypeConstant.VARCHAR, length = 50, comment = "权限名称")
    private String name;

    @Column(type = MySqlTypeConstant.BIGINT, comment = "父权限ID")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Long pid;

    @Unique
    @Column(type = MySqlTypeConstant.VARCHAR, length = 50, comment = "权限名")
    private String permission;

    @Column(type = MySqlTypeConstant.INT, length = 11, comment = "排序")
    private Integer sort;

}

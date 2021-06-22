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
 * @author Administrator
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "menu")
public class Menu extends BasePo {

    private static final long serialVersionUID = 3634741108814973621L;

    @Unique
    @IsNotNull
    @Column(type = MySqlTypeConstant.VARCHAR, length = 30, comment = "菜单名称'")
    private String name;

    @Column(type = MySqlTypeConstant.VARCHAR, length = 30, comment = "菜单图标'")
    private String icon;

    @Column(type = MySqlTypeConstant.BIGINT, comment = "父菜单ID'")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Long pid;

    @Column(type = MySqlTypeConstant.VARCHAR, comment = "菜单链接路径'")
    private String path;

    @Column(type = MySqlTypeConstant.INT, comment = "排序'")
    private Integer sort;

}

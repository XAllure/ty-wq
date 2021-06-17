package com.ty.wq.pojo.po.client;

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
@Table("authority")
public class Authority extends BasePo {

    private static final long serialVersionUID = 2428599904346805092L;

    @Column(type = MySqlTypeConstant.BIGINT, comment = "父权限ID")
    private Long parentId;

    @Unique
    @IsNotNull
    @Column(type = MySqlTypeConstant.VARCHAR, length = 50, comment = "名称")
    private String name;

    @Column(type = MySqlTypeConstant.VARCHAR, length = 128, comment = "权限名")
    private String auth;

    @Column(type = MySqlTypeConstant.VARCHAR, comment = "权限链接")
    private String url;

}

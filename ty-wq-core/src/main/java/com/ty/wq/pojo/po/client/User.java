package com.ty.wq.pojo.po.client;

import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.IsNotNull;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.annotation.Unique;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import com.ty.wq.pojo.po.BasePo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;

/**
 * 用户表
 * 如果创建的实体类名与数据库表名不一致
 * 需要加上@TableName("user")来保持一致
 * @author Administrator
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "user")
public class User extends BasePo {

    private static final long serialVersionUID = 7291243392404360159L;

    @Column(type = MySqlTypeConstant.VARCHAR, length = 128, comment = "头像")
    private String avatar;

    @Unique
    @IsNotNull
    @Column(type = MySqlTypeConstant.VARCHAR, length = 30, comment = "用户名")
    private String username;

    @IsNotNull
    @Column(type = MySqlTypeConstant.VARCHAR, length = 50, comment = "密码")
    private String password;

    @Unique
    @Column(type = MySqlTypeConstant.VARCHAR, length = 20, comment = "手机号")
    private String phone;

    @Unique
    @Column(type = MySqlTypeConstant.VARCHAR, length = 30, comment = "邮箱")
    private String email;

    @IsNotNull
    @Column(type = MySqlTypeConstant.VARCHAR, length = 50, comment = "加密的盐")
    private String salt;

    @Column(type = MySqlTypeConstant.INT, comment = "是否可创建群组 1可 0不可")
    private Integer createGroup;

    @Column(type = MySqlTypeConstant.DATETIME, comment = "登录时间")
    private Timestamp loginTime;

}

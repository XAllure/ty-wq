package com.ty.wq.pojo.po.manager;

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
 * @author Administrator
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Table("admin")
public class Admin extends BasePo {
    private static final long serialVersionUID = 3766305445311102988L;

    @Column(type = MySqlTypeConstant.VARCHAR, comment = "头像地址'")
    private String avatar;

    @Unique
    @IsNotNull
    @Column(type = MySqlTypeConstant.VARCHAR, length = 30, comment = "用户名'")
    private String username;

    @IsNotNull
    @Column(type = MySqlTypeConstant.VARCHAR, length = 50, comment = "密码'")
    private String password;

    @Column(type = MySqlTypeConstant.VARCHAR, length = 32, comment = "加密的盐'")
    private String salt;

    @Unique
    @Column(type = MySqlTypeConstant.VARCHAR, length = 11, comment = "手机号'")
    private String phone;

    @Column(type = MySqlTypeConstant.VARCHAR, length = 50, comment = "邮箱'")
    private String email;

    @Column(type = MySqlTypeConstant.VARCHAR, length = 128, comment = "谷歌验证码秘钥'")
    private String secretKey;

    @Column(type = MySqlTypeConstant.DATETIME, comment = "登录时间'")
    private Timestamp loginTime;

}

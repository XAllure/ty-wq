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
@Table(name = "company")
public class Company extends BasePo {

    private static final long serialVersionUID = 429288194304121977L;

    @Unique
    @IsNotNull
    @Column(type = MySqlTypeConstant.VARCHAR, length = 30, comment = "用户名")
    private String name;

    @Column(type = MySqlTypeConstant.BIGINT, comment = "父ID'")
    private Long pid;

    @Column(type = MySqlTypeConstant.VARCHAR, length = 20, comment = "手机号")
    private String phone;

    @Column(type = MySqlTypeConstant.VARCHAR, length = 50, comment = "公司邮箱")
    private String email;

}

package com.ty.wq.pojo.po.client;

import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.IsNotNull;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import com.ty.wq.pojo.po.BasePo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户部门表
 * @author Administrator
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "user_department")
@TableName("user_department")
public class UserDepartment extends BasePo {

    private static final long serialVersionUID = 6107493506315974309L;

    @IsNotNull
    @Column(type = MySqlTypeConstant.BIGINT, comment = "部门ID")
    private Long departmentId;

    @IsNotNull
    @Column(type = MySqlTypeConstant.BIGINT, comment = "用户ID")
    private Long userId;

}

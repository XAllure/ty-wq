package com.ty.wq.pojo.po.client;

import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.IsNotNull;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import com.ty.wq.pojo.po.BasePo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 部门权限表
 * @author Administrator
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "department_permission")
public class DepartmentPermission extends BasePo {

    private static final long serialVersionUID = 8449319116851398811L;

    @IsNotNull
    @Column(type = MySqlTypeConstant.BIGINT, comment = "部门ID")
    private Long departmentId;

    @IsNotNull
    @Column(type = MySqlTypeConstant.BIGINT, comment = "权限ID")
    private Long permissionId;

}

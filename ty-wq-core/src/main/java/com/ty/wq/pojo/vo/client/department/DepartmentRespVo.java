package com.ty.wq.pojo.vo.client.department;

import com.ty.wq.pojo.vo.BaseRespVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * @author Administrator
 * @version 1.0
 * @program: ty-wq
 * @description:
 * @date 2021-06-21 03:40:40
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DepartmentRespVo extends BaseRespVo {

    private static final long serialVersionUID = -1L;

    @ApiModelProperty("父ID")
    private Long pid;

    @ApiModelProperty("部门名称")
    private String name;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("公司ID")
    private Long companyId;

    @ApiModelProperty("公司名称")
    private String companyName;

    @ApiModelProperty("状态")
    private Integer status;

}

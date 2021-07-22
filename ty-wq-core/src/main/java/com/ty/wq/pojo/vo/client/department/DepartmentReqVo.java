package com.ty.wq.pojo.vo.client.department;

import com.ty.wq.pojo.vo.BaseReqVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * @author Administrator
 * @version 1.0
 * @program: ty-wq
 * @description:
 * @date 2021-06-21 03:40:40
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel
public class DepartmentReqVo extends BaseReqVo {

    private static final long serialVersionUID = -1L;

    @ApiModelProperty("父ID")
    private Long pid;

    @ApiModelProperty("部门名称")
    @NotBlank(message = "部门名称不能为空",groups = {Add.class, Update.class})
    private String name;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("公司ID")
    private Long companyId;

}

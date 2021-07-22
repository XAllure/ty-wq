package com.ty.wq.pojo.vo.client.company;

import com.ty.wq.pojo.vo.BaseReqVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author Administrator
 * @version 1.0
 * @program: ty-wq
 * @description:
 * @date 2021-06-21 03:40:12
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel
public class CompanyReqVo extends BaseReqVo {

    private static final long serialVersionUID = -1L;

    @ApiModelProperty("父ID")
    private Long pid;

    @ApiModelProperty("公司名称")
    @NotBlank(message = "公司名称不能为空",groups = {Add.class, Update.class})
    private String name;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("邮箱")
    private String email;

}

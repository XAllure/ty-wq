package com.ty.wq.pojo.vo.client.permission;

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
 * @date 2021-06-17 08:15:02
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel
public class PermissionReqVo extends BaseReqVo {

    private static final long serialVersionUID = -1L;

    @ApiModelProperty("父权限id")
    private Long pid;

    @ApiModelProperty("权限名称")
    @NotBlank(message = "名称不能为空",groups = {Add.class, Update.class})
    private String name;

    @ApiModelProperty("权限名")
    private String permission;

    @ApiModelProperty("排序")
    private String sort;

}

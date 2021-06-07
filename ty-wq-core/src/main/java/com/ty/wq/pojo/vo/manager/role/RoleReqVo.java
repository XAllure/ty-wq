package com.ty.wq.pojo.vo.manager.role;

import com.ty.wq.pojo.vo.BaseReqVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * @author Administrator
 * @version 1.0
 * @program: ty-wq
 * @description:
 * @date 2021-06-07 02:02:56
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RoleReqVo extends BaseReqVo {
    private static final long serialVersionUID = -1L;

    @NotBlank(message = "角色名称不能为空",groups = {Add.class, Update.class})
    private String name;

    private String introduce;

}

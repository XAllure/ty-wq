package com.ty.wq.pojo.vo.client.user;

import com.ty.wq.pojo.vo.BaseRespVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Administrator
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel
public class UserRespVo extends BaseRespVo {

    private static final long serialVersionUID = -7106325894945378008L;

    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("昵称")
    private String userNick;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("公司ID")
    private Long companyId;

    @ApiModelProperty("公司名称")
    private String companyName;

    @ApiModelProperty("部门ID")
    private Long departmentId;

    @ApiModelProperty("部门名称")
    private String departmentName;

    @ApiModelProperty("登录时间")
    private String loginTime;

    @ApiModelProperty("状态")
    private Integer status;

}

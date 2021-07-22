package com.ty.wq.pojo.vo.client.user;

import com.ty.wq.pojo.vo.BaseReqVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.sql.Timestamp;

/**
 * @author Administrator
 * @version 1.0
 * @program: ty-wq
 * @description:
 * @date 2021-04-28 04:15:44
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel
public class UserReqVo extends BaseReqVo {
    private static final long serialVersionUID = -1L;

    @ApiModelProperty("头像")
    @NotBlank(message = "头像不能为空",groups = Self.class)
    private String avatar;

    @ApiModelProperty("用户名")
    @NotBlank(message = "用户名不能为空",groups = {Add.class, Update.class, Self.class})
    private String username;

    @ApiModelProperty("昵称")
    @NotBlank(message = "昵称不能为空",groups = {Add.class, Update.class, Self.class})
    private String userNick;

    @ApiModelProperty("密码")
    @NotBlank(message = "密码不能为空",groups = {Add.class})
    @Length(min = 6,max = 40,message = "密码必须6-20位",groups = {Add.class})
    private String password;

    @ApiModelProperty("手机号")
    @Pattern(regexp = "^1[34578]\\d{9}$", message = "手机号的格式不对",groups = {Add.class, Update.class})
    private String phone;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("公司ID")
    @NotNull(message = "请选择公司",groups = {Add.class, Update.class})
    private Long companyId;

    @ApiModelProperty("部门ID")
    @NotNull(message = "请选择部门",groups = {Add.class, Update.class})
    private Long departmentId;

}

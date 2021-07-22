package com.ty.wq.pojo.vo.client.user;

import com.ty.wq.pojo.vo.BaseReqVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author Administrator
 */
@Data
@ApiModel
public class PasswordReqVo implements Serializable {

    private static final long serialVersionUID = -606983124630495502L;

    @ApiModelProperty("原密码")
    @NotBlank(message = "原密码不能为空",groups = {BaseReqVo.Self.class})
    private String oldPassword;

    @ApiModelProperty("新密码")
    @NotBlank(message = "新密码不能为空",groups = {BaseReqVo.Self.class})
    @Length(min = 6,max = 20,message = "密码必须6-20位",groups = {BaseReqVo.Self.class})
    private String password;

    @ApiModelProperty("确认密码")
    @NotBlank(message = "确认密码不能为空",groups = {BaseReqVo.Self.class})
    private String confirmPassword;
}

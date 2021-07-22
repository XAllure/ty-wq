package com.ty.wq.pojo.vo.client.user;

import com.ty.wq.pojo.vo.BaseReqVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author Administrator
 */
@Data
@ApiModel
public class LoginReqVo implements Serializable {

    private static final long serialVersionUID = -8903978321950217199L;

    @ApiModelProperty("用户名")
    @NotBlank(message = "用户名不能为空", groups = BaseReqVo.Login.class)
    private String username;

    @ApiModelProperty("密码")
    @NotBlank(message = "密码不能为空", groups = BaseReqVo.Login.class)
    private String password;

}

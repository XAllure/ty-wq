package com.ty.wq.pojo.vo.manager.admin;

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
    private static final long serialVersionUID = -111725533509243219L;

    @NotBlank(message = "用户名不能为空",groups = {BaseReqVo.Login.class})
    private String username;

    @NotBlank(message = "密码不能为空",groups = {BaseReqVo.Login.class})
    private String password;

    @NotBlank(message = "验证码不能为空",groups = {BaseReqVo.Login.class})
    private String code;

}

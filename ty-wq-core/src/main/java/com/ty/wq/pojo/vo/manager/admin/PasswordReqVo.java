package com.ty.wq.pojo.vo.manager.admin;

import com.ty.wq.pojo.vo.BaseReqVo;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author Administrator
 */
@Data
public class PasswordReqVo implements Serializable {
    private static final long serialVersionUID = -606983124630495502L;

    @NotBlank(message = "密码不能为空",groups = {BaseReqVo.Self.class})
    @Length(min = 6,max = 40,message = "密码必须6-20位",groups = {BaseReqVo.Add.class})
    private String password;

    @NotBlank(message = "二次密码不能为空",groups = {BaseReqVo.Self.class})
    private String confirmPassword;

    @NotBlank(message = "验证码不能为空",groups = {BaseReqVo.Self.class})
    private String code;

}

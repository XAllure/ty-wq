package com.ty.wq.pojo.vo.manager.admin;

import com.ty.wq.pojo.vo.BaseReqVo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author Administrator
 * @version 1.0
 * @program: ty-wq
 * @description:
 * @date 2021-06-02 09:41:11
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AdminReqVo extends BaseReqVo {
    private static final long serialVersionUID = -1L;

    @NotBlank(message = "头像不能为空",groups = Self.class)
    private String avatar;

    @NotBlank(message = "用户名不能为空",groups = {Add.class, Update.class, Self.class})
    private String username;

    @NotBlank(message = "密码不能为空",groups = {Add.class})
    @Length(min = 6,max = 40,message = "密码必须6-20位",groups = {Add.class})
    private String password;

    @Pattern(regexp = "^1[34578]\\d{9}$", message = "手机号的格式不对",groups = {Add.class, Update.class, Self.class})
    private String phone;

    private String email;

}

package com.ty.wq.pojo.vo.client.user;

import com.ty.wq.pojo.vo.BaseReqVo;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * @author xuxilan
 */
@Data
public class PhoneReqVo implements Serializable {
    private static final long serialVersionUID = -9137194110680746074L;

    @NotBlank(message = "请输入手机号", groups = {BaseReqVo.Update.class})
    @Pattern(regexp = "^1[34578]\\d{9}$", message = "手机号的格式不对",groups = {BaseReqVo.Update.class})
    private String phone;

    @NotBlank(message = "请输入短信验证码", groups = {BaseReqVo.Update.class})
    private String code;
}

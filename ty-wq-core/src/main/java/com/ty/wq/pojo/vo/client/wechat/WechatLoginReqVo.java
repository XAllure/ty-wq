package com.ty.wq.pojo.vo.client.wechat;

import com.ty.wq.pojo.vo.BaseReqVo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author Administrator
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class WechatLoginReqVo extends BaseReqVo {

    private static final long serialVersionUID = 8373986222579201102L;

    @NotBlank(message = "请选择要登录的微信号", groups = {Login.class})
    private List<String> weChatIds;

}

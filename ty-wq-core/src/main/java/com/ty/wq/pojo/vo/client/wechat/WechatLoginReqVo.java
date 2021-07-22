package com.ty.wq.pojo.vo.client.wechat;

import com.ty.wq.pojo.vo.BaseReqVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author Administrator
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel
public class WechatLoginReqVo extends BaseReqVo {

    private static final long serialVersionUID = 8373986222579201102L;

    @ApiModelProperty("要登录的微信")
    @NotEmpty(message = "请选择要登录的微信", groups = {Login.class})
    private List<String> wechatIds;

}

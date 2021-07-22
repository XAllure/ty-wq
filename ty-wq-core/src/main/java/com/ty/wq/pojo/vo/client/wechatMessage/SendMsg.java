package com.ty.wq.pojo.vo.client.wechatMessage;

import com.ty.wq.pojo.vo.BaseReqVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @author Administrator
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel
public class SendMsg {

    @ApiModelProperty("api参数")
    @NotBlank(message = "api参数不能为空", groups = BaseReqVo.Chat.class)
    private String api;

    @ApiModelProperty("sendId参数 请把微信id作为sendId")
    @NotBlank(message = "sendId参数不能为空，请把微信id作为sendId", groups = BaseReqVo.Chat.class)
    private String sendId;

    @ApiModelProperty("数据")
    private Object option;
}

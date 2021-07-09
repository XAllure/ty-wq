package com.ty.wq.pojo.vo.client.wechatMessage;

import com.ty.wq.pojo.vo.BaseReqVo;
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
public class SendMsg {

    @NotBlank(message = "api参数不能为空", groups = BaseReqVo.Chat.class)
    private String api;

    @NotBlank(message = "sendId参数不能为空，请把微信id作为sendId", groups = BaseReqVo.Chat.class)
    private String sendId;

    private Object option;
}

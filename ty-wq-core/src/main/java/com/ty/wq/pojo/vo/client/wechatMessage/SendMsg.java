package com.ty.wq.pojo.vo.client.wechatMessage;

import lombok.Data;

/**
 * @author Administrator
 */
@Data
public class SendMsg {
    private String api;
    private String sendId;
    private Object option;
}

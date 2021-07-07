package com.ty.wq.pojo.vo.client.wechatMessage;

import lombok.Data;

/**
 * @author Administrator
 */
@Data
public class ReceiveMsg {
    private String action;
    private String cwxid;
    private Object data;
}

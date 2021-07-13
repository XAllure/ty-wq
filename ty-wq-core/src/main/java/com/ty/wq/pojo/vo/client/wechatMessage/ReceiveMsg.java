package com.ty.wq.pojo.vo.client.wechatMessage;

import lombok.Data;

/**
 * @author Administrator
 */
@Data
public class ReceiveMsg {
    /** 上报的名称 */
    private String action;

    /** 当前登录微信账号的微信 ID */
    private String cwxid;

    /** 上报的数据 */
    private Object data;
}

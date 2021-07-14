package com.ty.wq.vo;

import lombok.Data;

/**
 * @author Administrator
 */
@Data
public class WechatFriendAddVo {

    private Integer msgType;

    private Integer ispc;

    private String msgid;

    private String roomWxid;

    private String wxidFrom;

    private String wxidTo;

    private String xmlmsg;

    private Long timestamp;

}

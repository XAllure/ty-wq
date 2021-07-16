package com.ty.wq.pojo.vo.netty.report;

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

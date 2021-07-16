package com.ty.wq.pojo.vo.netty.report;

import lombok.Data;

/**
 * @author Administrator
 */
@Data
public class ChatRoomMembersVo {

    /** 群的微信 ID */
    private String wxid;

    /** 群成员数量 */
    private Integer roomCount;

    /** String 类型的用户列表 */
    private String userLists;

}

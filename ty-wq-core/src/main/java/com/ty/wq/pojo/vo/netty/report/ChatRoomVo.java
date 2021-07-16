package com.ty.wq.pojo.vo.netty.report;

import lombok.Data;

/**
 * @author Administrator
 */
@Data
public class ChatRoomVo {

    private String wxid;

    private String nick;

    private String owner;

    private Integer isowner;

    private String headPic;

    private Integer roomCount;

    private String userLists;

}

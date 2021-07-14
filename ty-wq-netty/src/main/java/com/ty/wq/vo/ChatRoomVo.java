package com.ty.wq.vo;

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

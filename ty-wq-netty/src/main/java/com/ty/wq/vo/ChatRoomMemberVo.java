package com.ty.wq.vo;

import lombok.Data;

@Data
public class ChatRoomMemberVo {

    private String wxid;

    private String alias;

    private String nick;

    private String displayname;

    private String remark;

    private String headPic;

    private Integer sex;

    private String country;

    private String province;

    private String city;

}

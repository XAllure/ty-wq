package com.ty.wq.pojo.vo.netty.report;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Administrator
 */
@Data
@ApiModel
public class ChatRoomVo {

    @ApiModelProperty("群的微信ID")
    private String wxid;

    @ApiModelProperty("群昵称")
    private String nick;

    @ApiModelProperty("群主的wxid")
    private String owner;

    @ApiModelProperty("是否为群主")
    private Integer isowner;

    @ApiModelProperty("群头像")
    private String headPic;

    @ApiModelProperty("群成员数量")
    private Integer roomCount;

    @ApiModelProperty("当前群的成员wxid的列表")
    private String userLists;

}

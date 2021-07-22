package com.ty.wq.pojo.vo.netty.report;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Administrator
 */
@Data
@ApiModel
public class ChatRoomMembersVo {

    @ApiModelProperty("群的微信ID")
    private String wxid;

    @ApiModelProperty("群成员数量")
    private Integer roomCount;

    @ApiModelProperty("字符串类型的用户列表")
    private String userLists;

}

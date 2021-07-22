package com.ty.wq.pojo.vo.netty.report;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Administrator
 */
@Data
@ApiModel
public class ChatRoomMemberVo {

    @ApiModelProperty("群成员微信ID")
    private String wxid;

    @ApiModelProperty("微信号(有可能为空)")
    private String alias;

    @ApiModelProperty("群成员微信昵称")
    private String nick;

    @ApiModelProperty("群昵称")
    private String displayname;

    @ApiModelProperty("好友备注")
    private String remark;

    @ApiModelProperty("头像")
    private String headPic;

    @ApiModelProperty("性别:1男，2女,0未知")
    private Integer sex;

    @ApiModelProperty("祖国(可能为空)")
    private String country;

    @ApiModelProperty("省份(可能为空)")
    private String province;

    @ApiModelProperty("城市(可能为空)")
    private String city;

}

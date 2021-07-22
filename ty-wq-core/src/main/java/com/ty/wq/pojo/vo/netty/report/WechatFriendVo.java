package com.ty.wq.pojo.vo.netty.report;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 对应微信上报的好友信息
 * @author Administrator
 */
@Data
@ApiModel
public class WechatFriendVo {

    @ApiModelProperty("微信id")
    private String wxid;

    @ApiModelProperty("微信号(有可能为空)")
    private String alias;

    @ApiModelProperty("微信昵称")
    private String nick;

    @ApiModelProperty("好友备注")
    private String remark;

    @ApiModelProperty("高清头像的url地址")
    private String headPic;

    @ApiModelProperty("小头像的url地址")
    private String smallPic;

    @ApiModelProperty("性别:1男，2女,0(未知)")
    private Integer sex;

    @ApiModelProperty("祖国(可能为空)")
    private String country;

    @ApiModelProperty("省份(可能为空)")
    private String province;

    @ApiModelProperty("城市(可能为空)")
    private String city;

    @ApiModelProperty("朋友圈个性签名")
    private String signature;

    @ApiModelProperty("朋友圈背景图片")
    private String snspic;

    @ApiModelProperty("来源类型")
    private Integer scene;

    @ApiModelProperty("是否成功 1成功,0失败")
    private Integer status;

    @ApiModelProperty("用户v1")
    private String v1;

    @ApiModelProperty("用户v2")
    private String v2;

    @ApiModelProperty("查询任意微信时的查询内容")
    private String search;

    @ApiModelProperty("查询任意微信时的是否好友")
    private String isFriend;

}

package com.ty.wq.pojo.vo.client.wechatFriend;

import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.IsNotNull;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import com.ty.wq.pojo.vo.BaseRespVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.sql.Timestamp;

/**
 * @author Administrator
 * @version 1.0
 * @program: ty-wq
 * @description:
 * @date 2021-06-21 03:40:57
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel
public class WechatFriendRespVo extends BaseRespVo {

    private static final long serialVersionUID = -1L;

    @ApiModelProperty("微信id")
    private String wechatId;

    @ApiModelProperty("好友微信id")
    private String friendId;

    @ApiModelProperty("好友微信号")
    private String friendWechatNo;

    @ApiModelProperty("好友微信昵称")
    private String friendWechatNick;

    @ApiModelProperty("头像")
    private String headPic;

    @ApiModelProperty("性别 0-未知,1-男,2-女")
    private Integer gender;

    @ApiModelProperty("国家")
    private String country;

    @ApiModelProperty("省份")
    private String province;

    @ApiModelProperty("城市")
    private String city;

    @ApiModelProperty("公司ID")
    private Long companyId;

    @ApiModelProperty("部门ID")
    private Long departmentId;

    @ApiModelProperty("状态")
    private Integer status;

    @ApiModelProperty("是否置顶 0正常 1置顶")
    private Integer top;

    @ApiModelProperty("是否免打扰 0正常 1免打扰")
    private Integer disturb;

    @ApiModelProperty("朋友圈个性签名")
    private String signature;

    @ApiModelProperty("朋友圈背景图片")
    private String snsPic;

    @ApiModelProperty("来源类型 6-好友验证 14-添加群好友 15-通过查询添加 17-通过名片添加，需要传v1值")
    private Integer scene;

    @ApiModelProperty("用户v1")
    private String v1;

    @ApiModelProperty("用户v2")
    private String v2;

    @ApiModelProperty("备注姓名")
    private String remarkName;

    @ApiModelProperty("备注电话号码")
    private String remarkPhone;

    @ApiModelProperty("备注标签")
    private String remarkTags;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("上次联系时间")
    private String contactTime;

}

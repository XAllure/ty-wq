package com.ty.wq.pojo.vo.client.wechatFriend;

import com.ty.wq.pojo.vo.BaseReqVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
public class WechatFriendReqVo extends BaseReqVo {

    private static final long serialVersionUID = -1L;

    @ApiModelProperty("微信id")
    @NotBlank(message = "微信id不能为空", groups = {Info.class, Add.class, Update.class, Delete.class, Status.class, Top.class, Disturb.class})
    private String wechatId;

    @ApiModelProperty("好友微信id")
    @NotBlank(message = "好友微信id不能为空", groups = {Info.class, Add.class, Update.class, Delete.class, Status.class, Top.class, Disturb.class})
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
    @NotNull(message = "公司ID不能为空", groups = {Update.class})
    private Long companyId;

    @ApiModelProperty("部门ID")
    @NotNull(message = "部门ID不能为空", groups = {Update.class})
    private Long departmentId;

    @ApiModelProperty("状态")
    @NotNull(message = "好友状态不能为空", groups = {Update.class, Status.class})
    private Integer status;

    @ApiModelProperty("是否置顶 0正常 1置顶")
    @NotNull(message = "请选择是否置顶", groups = {Top.class})
    private Integer top;

    @ApiModelProperty("是否免打扰 0正常 1免打扰")
    @NotNull(message = "请选择是否免打扰", groups = {Disturb.class})
    private Integer disturb;

    @ApiModelProperty("朋友圈个性签名")
    private String signature;

    @ApiModelProperty("朋友圈背景图片")
    private String snsPic;

    @ApiModelProperty("来源类型 6-好友验证 14-添加群好友 15-通过查询添加 17-通过名片添加，需要传v1值")
    @NotNull(message = "好友来源类型不能为空", groups = {Add.class})
    private Integer scene;

    @ApiModelProperty("用户v1")
    private String v1;

    @ApiModelProperty("用户v2")
    private String v2;

    @ApiModelProperty("备注姓名")
    @NotBlank(message = "备注姓名为空", groups = {Update.class})
    private String remarkName;

    @ApiModelProperty("备注电话号码")
    private String remarkPhone;

    @ApiModelProperty("备注标签")
    private String remarkTags;

    @ApiModelProperty("备注")
    @NotBlank(message = "备注为空", groups = {Add.class})
    private String remark;

    @ApiModelProperty("添加好友时来自哪个群，可空")
    private String chatRoomId;

    @ApiModelProperty("上次联系时间")
    private String contactTime;

}

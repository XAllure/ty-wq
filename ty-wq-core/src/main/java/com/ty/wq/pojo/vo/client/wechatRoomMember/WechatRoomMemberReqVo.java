package com.ty.wq.pojo.vo.client.wechatRoomMember;

import com.ty.wq.pojo.vo.BaseReqVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.List;

/**
 * @author Administrator
 * @version 1.0
 * @program: ty-wq
 * @description:
 * @date 2021-06-21 03:41:36
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel
public class WechatRoomMemberReqVo extends BaseReqVo {

    private static final long serialVersionUID = -1L;

    @ApiModelProperty("群主微信ID")
    @NotBlank(message = "群主微信ID不能为空", groups = {Add.class})
    private String ownerWechatId;

    @ApiModelProperty("群ID")
    @NotBlank(message = "群ID不能为空", groups = {Add.class, Update.class, Info.class, Out.class, Invite.class})
    private String chatRoomId;

    @ApiModelProperty("微信ID")
    @NotBlank(message = "微信ID不能为空", groups = {Add.class, Update.class, Info.class, Out.class, Invite.class})
    private String wechatId;

    @ApiModelProperty("微信号")
    private String wechatNo;

    @ApiModelProperty("微信昵称")
    @NotBlank(message = "微信昵称不能为空", groups = {Add.class})
    private String wechatNick;

    @ApiModelProperty("高清头像")
    @NotBlank(message = "高清头像不能为空", groups = {Add.class})
    private String headPic;

    @ApiModelProperty("性别 0-未知,1-男,2-女")
    @NotNull(message = "性别不能为空", groups = {Add.class})
    private Integer gender;

    @ApiModelProperty("国家")
    private String country;

    @ApiModelProperty("省份")
    private String province;

    @ApiModelProperty("城市")
    private String city;

    @ApiModelProperty("群昵称")
    @NotBlank(message = "群昵称不能为空", groups = {Add.class, Update.class})
    private String displayName;

    @ApiModelProperty("好友备注")
    @NotBlank(message = "好友备注不能为空", groups = {Add.class})
    private String remark;

    @ApiModelProperty("公司ID")
    @NotNull(message = "公司ID不能为空", groups = {Add.class})
    private Long companyId;

    @ApiModelProperty("部门ID")
    @NotNull(message = "部门ID不能为空", groups = {Add.class})
    private Long departmentId;

    @ApiModelProperty("要踢的群成员/邀请入群的成员列表")
    @NotEmpty(message = "请选择群成员", groups = {Out.class, Invite.class})
    private List<String> wxidList;

}

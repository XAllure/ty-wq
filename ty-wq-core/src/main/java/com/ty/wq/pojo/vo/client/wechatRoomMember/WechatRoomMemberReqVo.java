package com.ty.wq.pojo.vo.client.wechatRoomMember;

import com.ty.wq.pojo.vo.BaseReqVo;
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
public class WechatRoomMemberReqVo extends BaseReqVo {

    private static final long serialVersionUID = -1L;

    /** 拥有者微信ID */
    @NotBlank(message = "群主ID不能为空", groups = {Add.class})
    private String ownerWechatId;

    /** 群ID */
    @NotBlank(message = "群ID不能为空", groups = {Add.class, Update.class, Info.class, Out.class, Invite.class})
    private String chatRoomId;

    /** 微信ID */
    @NotBlank(message = "微信ID不能为空", groups = {Add.class, Update.class, Info.class, Out.class, Invite.class})
    private String wechatId;

    /** 微信号 */
    private String wechatNo;

    /** 微信昵称 */
    @NotBlank(message = "微信昵称不能为空", groups = {Add.class})
    private String wechatNick;

    /** 高清头像 */
    @NotBlank(message = "高清头像不能为空", groups = {Add.class})
    private String headPic;

    /** 性别 0-未知,1-男,2-女 */
    @NotNull(message = "性别不能为空", groups = {Add.class})
    private Integer gender;

    /** 国家 */
    private String country;

    /** 省份 */
    private String province;

    /** 城市 */
    private String city;

    /** 群昵称 */
    @NotBlank(message = "群昵称不能为空", groups = {Add.class, Update.class})
    private String displayName;

    /** 好友备注 */
    @NotBlank(message = "好友备注不能为空", groups = {Add.class})
    private String remark;

    /** 公司ID */
    @NotNull(message = "公司ID不能为空", groups = {Add.class})
    private Long companyId;

    /** 部门ID */
    @NotNull(message = "部门ID不能为空", groups = {Add.class})
    private Long departmentId;

    /** 要踢的群成员/邀请入群的成员列表 */
    @NotEmpty(message = "请选择群成员", groups = {Out.class, Invite.class})
    private List<String> wxidList;

}

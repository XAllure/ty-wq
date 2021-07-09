package com.ty.wq.pojo.vo.client.wechatFriend;

import com.ty.wq.pojo.vo.BaseReqVo;
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
public class WechatFriendReqVo extends BaseReqVo {

    private static final long serialVersionUID = -1L;

    @NotBlank(message = "微信id不能为空", groups = {Info.class, Add.class, Update.class, Remark.class, Delete.class, Status.class, Top.class, Disturb.class})
    private String wechatId;

    /** 好友微信id */
    @NotBlank(message = "好友微信id不能为空", groups = {Info.class, Add.class, Update.class, Remark.class, Delete.class, Status.class, Top.class, Disturb.class})
    private String friendId;

    /** 好友微信号 */
    private String friendWeChatNo;

    /** 好友微信昵称 */
    @NotBlank(message = "好友微信昵称不能为空", groups = {Add.class, Update.class})
    private String friendWeChatNick;

    /** 头像 */
    @NotBlank(message = "好友头像不能为空", groups = {Add.class, Update.class})
    private String headPic;

    /** 性别 0-未知,1-男,2-女 */
    @NotNull(message = "好友性别不能为空", groups = {Add.class, Update.class})
    private Integer gender;

    /** 国家 */
    private String country;

    /** 省份 */
    private String province;

    /** 城市 */
    private String city;

    /** 公司ID */
    @NotNull(message = "公司ID不能为空", groups = {Update.class})
    private Long companyId;

    /** 部门ID */
    @NotNull(message = "部门ID不能为空", groups = {Update.class})
    private Long departmentId;

    /** 状态 */
    @NotNull(message = "好友状态不能为空", groups = {Update.class, Status.class})
    private Integer status;

    /** 是否置顶 0正常 1置顶 */
    @NotBlank(message = "请选择是否置顶", groups = {Top.class})
    private Integer top;

    /** 是否免打扰 0正常 1免打扰 */
    @NotNull(message = "请选择是否免打扰", groups = {Disturb.class})
    private Integer disturb;

    /** 朋友圈个性签名 */
    private String signature;

    /** 朋友圈背景图片 */
    private String snsPic;

    /** 来源类型 6-好友验证 14-添加群好友 15-通过查询添加 17-通过名片添加，需要传v1值 */
    @NotNull(message = "好友来源类型不能为空", groups = {Add.class})
    private Integer scene;

    /** 用户v1 */
    private String v1;

    /** 用户v2 */
    private String v2;

    /** 备注姓名 */
    private String remarkName;

    /** 备注电话号码 */
    private String remarkPhone;

    /** 备注标签 */
    private String remarkTags;

    /** 备注 */
    @NotBlank(message = "备注为空", groups = {Add.class, Remark.class})
    private String remark;

    /** 上次联系时间 */
    private String contactTime;

}

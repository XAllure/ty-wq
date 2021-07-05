package com.ty.wq.pojo.vo.client.wechat;

import com.ty.wq.pojo.vo.BaseReqVo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Administrator
 * @version 1.0
 * @program: ty-wq
 * @description:
 * @date 2021-06-15 07:08:40
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class WechatReqVo extends BaseReqVo {
    private static final long serialVersionUID = -1L;

    @NotBlank(message = "微信ID不能为空", groups = {Update.class})
    private String wechatId;

    /** 微信号 */
    private String wechatNo;

    @NotBlank(message = "微信昵称不能为空", groups = {Update.class})
    private String wechatNick;

    /** 高清头像 */
    private String headPic;

    /** 小头像 */
    private String smallPic;

    @NotNull(message = "性别不能为空", groups = {Update.class})
    private Integer gender;

    @NotBlank(message = "请选择国家", groups = {Update.class})
    private String country;

    @NotBlank(message = "请选择省份", groups = {Update.class})
    private String province;

    @NotBlank(message = "请选择城市", groups = {Update.class})
    private String city;

    /** 朋友圈个性签名 */
    private String signature;

    @NotBlank(message = "朋友圈背景图片不能为空", groups = {Update.class})
    private String snsPic;

    /** 设备ID */
    private String deviceId;

    /** 公司ID */
    private Long companyId;

    /** 部门ID */
    private Long departmentId;

}

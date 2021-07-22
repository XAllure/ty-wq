package com.ty.wq.pojo.vo.client.wechat;

import com.ty.wq.pojo.vo.BaseReqVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel
public class WechatReqVo extends BaseReqVo {
    private static final long serialVersionUID = -1L;

    @ApiModelProperty("微信ID")
    @NotBlank(message = "微信ID不能为空", groups = {Update.class})
    private String wechatId;

    @ApiModelProperty("微信号")
    private String wechatNo;

    @ApiModelProperty("微信昵称")
    @NotBlank(message = "微信昵称不能为空", groups = {Update.class})
    private String wechatNick;

    @ApiModelProperty("高清头像")
    private String headPic;

    @ApiModelProperty("小头像")
    private String smallPic;

    @ApiModelProperty("性别")
    @NotNull(message = "性别不能为空", groups = {Update.class})
    private Integer gender;

    @ApiModelProperty("国家")
    private String country;

    @ApiModelProperty("省份")
    private String province;

    @ApiModelProperty("城市")
    private String city;

    @ApiModelProperty("朋友圈个性签名")
    private String signature;

    @ApiModelProperty("朋友圈背景图片")
    @NotBlank(message = "朋友圈背景图片不能为空", groups = {Update.class})
    private String snsPic;

    @ApiModelProperty("设备ID")
    private String deviceId;

    @ApiModelProperty("公司ID")
    private Long companyId;

    @ApiModelProperty("部门ID")
    private Long departmentId;

}

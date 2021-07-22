package com.ty.wq.pojo.vo.client.wechat;

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
 * @date 2021-06-15 07:08:40
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel
public class WechatRespVo extends BaseRespVo {

    private static final long serialVersionUID = -1L;

    @ApiModelProperty("微信ID")
    private String wechatId;

    @ApiModelProperty("微信号")
    private String wechatNo;

    @ApiModelProperty("微信昵称")
    private String wechatNick;

    @ApiModelProperty("高清头像")
    private String headPic;

    @ApiModelProperty("小头像")
    private String smallPic;

    @ApiModelProperty("性别")
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
    private String snsPic;

    @ApiModelProperty("是否在线")
    private Integer isOnline;

    @ApiModelProperty("是否已登录")
    private Integer isLogin;

    @ApiModelProperty("设备ID")
    private String deviceId;

    @ApiModelProperty("状态")
    private Integer status;

    @ApiModelProperty("公司ID")
    private Long companyId;

    @ApiModelProperty("公司名称")
    private String companyName;

    @ApiModelProperty("部门ID")
    private Long departmentId;

    @ApiModelProperty("部门名称")
    private String departmentName;

    @ApiModelProperty("登录时间")
    private String loginTime;

}

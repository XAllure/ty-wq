package com.ty.wq.pojo.vo.client.wechat;

import com.ty.wq.pojo.vo.BaseRespVo;
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
public class WechatRespVo extends BaseRespVo {

    private static final long serialVersionUID = -1L;

    /** 微信id */
    private String wechatId;

    /** 微信号 */
    private String wechatNo;

    /** 微信昵称 */
    private String wechatNick;

    /** 高清头像 */
    private String headPic;

    /** 小头像 */
    private String smallPic;

    /** 性别 */
    private Integer gender;

    /** 国家 */
    private String country;

    /** 省份 */
    private String province;

    /** 城市 */
    private String city;

    /** 朋友圈个性签名 */
    private String signature;

    /** 朋友圈背景图片 */
    private String snsPic;

    /** 是否在线 */
    private Integer isOnline;

    /** 是否已登录 */
    private Integer isLogin;

    /** 设备ID */
    private String deviceId;

    /** 状态 */
    private Integer status;

    /** 公司ID */
    private Long companyId;

    /** 公司名称 */
    private String companyName;

    /** 部门ID */
    private Long departmentId;

    /** 部门名称 */
    private String departmentName;

    /** 登录时间 */
    private String loginTime;

}

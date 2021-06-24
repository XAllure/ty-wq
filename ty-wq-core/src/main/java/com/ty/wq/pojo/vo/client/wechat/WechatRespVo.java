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

    private Long id;

    private String wechatId;

    private String wechatNo;

    private String wechatNick;

    private String headPic;

    private String smallPic;

    private Integer gender;

    private String country;

    private String province;

    private String city;

    private String signature;

    private String snsPic;

    private Integer isOnline;

    private Integer isLogin;

    private String deviceId;

    private Long companyId;

    private String loginTime;

    private String companyName;

    private Long departmentId;

    private String departmentName;

}

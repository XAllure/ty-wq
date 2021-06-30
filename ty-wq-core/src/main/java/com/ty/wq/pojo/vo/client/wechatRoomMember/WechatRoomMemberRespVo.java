package com.ty.wq.pojo.vo.client.wechatRoomMember;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import com.ty.wq.pojo.vo.BaseRespVo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.sql.Timestamp;

/**
 * @author Administrator
 * @version 1.0
 * @program: ty-wq
 * @description:
 * @date 2021-06-21 03:41:36
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class WechatRoomMemberRespVo extends BaseRespVo {

    private static final long serialVersionUID = -1L;

    /** 拥有者微信ID */
    private String ownerWechatId;

    /** 群ID */
    private String chatRoomId;

    /** 微信id */
    private String wechatId;

    /** 微信号 */
    private String wechatNo;

    /** 微信昵称 */
    private String wechatNick;

    /** 高清头像 */
    private String headPic;

    /** 性别 0-未知,1-男,2-女 */
    private Integer gender;

    /** 国家 */
    private String country;

    /** 省份 */
    private String province;

    /** 城市 */
    private String city;

    /** 群昵称 */
    private String displayName;

    /** 好友备注 */
    private String remark;

    /** 公司ID */
    private Long companyId;

    /** 公司名称 */
    private String companyName;

    /** 部门ID */
    private Long departmentId;

    /** 部门名称 */
    private String departmentName;

}

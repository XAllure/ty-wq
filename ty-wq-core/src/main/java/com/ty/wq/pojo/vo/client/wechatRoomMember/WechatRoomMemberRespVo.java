package com.ty.wq.pojo.vo.client.wechatRoomMember;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
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
 * @date 2021-06-21 03:41:36
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel
public class WechatRoomMemberRespVo extends BaseRespVo {

    private static final long serialVersionUID = -1L;

    @ApiModelProperty("群主微信ID")
    private String ownerWechatId;

    @ApiModelProperty("群ID")
    private String chatRoomId;

    @ApiModelProperty("微信ID")
    private String wechatId;

    @ApiModelProperty("微信号")
    private String wechatNo;

    @ApiModelProperty("微信昵称")
    private String wechatNick;

    @ApiModelProperty("高清头像")
    private String headPic;

    @ApiModelProperty("性别 0-未知,1-男,2-女")
    private Integer gender;

    @ApiModelProperty("国家")
    private String country;

    @ApiModelProperty("省份")
    private String province;

    @ApiModelProperty("城市")
    private String city;

    @ApiModelProperty("群昵称")
    private String displayName;

    @ApiModelProperty("好友备注")
    private String remark;

    @ApiModelProperty("公司ID")
    private Long companyId;

    @ApiModelProperty("公司名称")
    private String companyName;

    @ApiModelProperty("部门ID")
    private Long departmentId;

    @ApiModelProperty("部门名称")
    private String departmentName;

}

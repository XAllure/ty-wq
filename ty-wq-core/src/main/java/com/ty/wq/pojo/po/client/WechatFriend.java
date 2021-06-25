package com.ty.wq.pojo.po.client;

import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.IsNotNull;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import com.ty.wq.pojo.po.BasePo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 微信好友表
 * @author Administrator
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Table("wechat_friend")
@TableName("wechat_friend")
public class WechatFriend extends BasePo {

    private static final long serialVersionUID = -1513515269846467582L;

    @Column( type = MySqlTypeConstant.VARCHAR, length = 128, comment = "微信id")
    private String wechatId;

    @Column( type = MySqlTypeConstant.VARCHAR, length = 128, comment = "好友微信id")
    private String friendId;

    @Column( type = MySqlTypeConstant.VARCHAR, length = 128, comment = "好友微信号")
    private String friendWeChatNo;

    @Column( type = MySqlTypeConstant.VARCHAR, length = 128, comment = "好友微信昵称")
    private String friendWeChatNick;

    @Column( type = MySqlTypeConstant.VARCHAR, comment = "头像")
    private String headPic;

    @Column( type = MySqlTypeConstant.INT, length = 1, comment = "性别 0-未知,1-男,2-女")
    private Integer gender;

    @Column( type = MySqlTypeConstant.VARCHAR, length = 128, comment = "国家")
    private String country;

    @Column( type = MySqlTypeConstant.VARCHAR, length = 128, comment = "省份")
    private String province;

    @Column( type = MySqlTypeConstant.VARCHAR, length = 128, comment = "城市")
    private String city;

    @IsNotNull
    @Column( type = MySqlTypeConstant.BIGINT, comment = "公司ID")
    private Long companyId;

    @IsNotNull
    @Column( type = MySqlTypeConstant.BIGINT, comment = "部门ID")
    private Long departmentId;

    @Column( type = MySqlTypeConstant.BIGINT, length = 1, comment = "是否置顶 0正常 1置顶")
    private Integer top;

    @Column( type = MySqlTypeConstant.BIGINT, length = 1, comment = "是否免打扰 0正常 1免打扰")
    private Integer disturb;

    @Column( type = MySqlTypeConstant.VARCHAR, comment = "朋友圈个性签名")
    private String signature;

    @Column( type = MySqlTypeConstant.VARCHAR, comment = "朋友圈背景图片")
    private String snsPic;

    @Column( type = MySqlTypeConstant.BIGINT, length = 11, comment = "来源类型 6-好友验证 14-添加群好友 15-通过查询添加 17-通过名片添加，需要传v1值")
    private Integer scene;

    @Column( type = MySqlTypeConstant.VARCHAR, comment = "用户v1")
    private String v1;

    @Column( type = MySqlTypeConstant.VARCHAR, comment = "用户v2")
    private String v2;

    @Column( type = MySqlTypeConstant.VARCHAR, length = 128, comment = "备注姓名")
    private String remarkName;

    @Column( type = MySqlTypeConstant.VARCHAR, length = 20, comment = "备注电话号码")
    private String remarkPhone;

    @Column( type = MySqlTypeConstant.VARCHAR, comment = "备注标签")
    private String remarkTags;

    @Column( type = MySqlTypeConstant.VARCHAR, comment = "备注")
    private String remark;

    @Column( type = MySqlTypeConstant.DATETIME, comment = "上次联系时间")
    private String contactTime;

}

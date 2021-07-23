package com.ty.wq.pojo.po.client;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.IsNotNull;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import com.ty.wq.pojo.po.BasePo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 微信群成员表
 * @author Administrator
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Table("wechat_room_member")
@TableName("wechat_room_member")
public class WechatRoomMember extends BasePo {

    private static final long serialVersionUID = -371758925001449762L;

    @IsNotNull
    @Column( type = MySqlTypeConstant.BIGINT, comment = "微信群表的主键id")
    private Long roomId;

    @Column( type = MySqlTypeConstant.VARCHAR, length = 128, comment = "拥有者微信ID")
    private String ownerWechatId;

    @Column( type = MySqlTypeConstant.VARCHAR, length = 128, comment = "微信群ID")
    private String chatRoomId;

    @Column( type = MySqlTypeConstant.VARCHAR, length = 128, comment = "微信id")
    private String wechatId;

    @Column( type = MySqlTypeConstant.VARCHAR, length = 128, comment = "微信号")
    private String wechatNo;

    @Column( type = MySqlTypeConstant.VARCHAR, length = 128, comment = "微信昵称")
    private String wechatNick;

    @Column( type = MySqlTypeConstant.VARCHAR, comment = "高清头像")
    private String headPic;

    @Column( type = MySqlTypeConstant.INT, length = 1, comment = "性别 0-未知,1-男,2-女")
    private Integer gender;

    @Column( type = MySqlTypeConstant.VARCHAR, length = 128, comment = "国家")
    private String country;

    @Column( type = MySqlTypeConstant.VARCHAR, length = 128, comment = "省份")
    private String province;

    @Column( type = MySqlTypeConstant.VARCHAR, length = 128, comment = "城市")
    private String city;

    @Column( type = MySqlTypeConstant.VARCHAR, length = 128, comment = "群昵称")
    private String displayName;

    @Column( type = MySqlTypeConstant.VARCHAR, length = 128, comment = "好友备注")
    private String remark;

    @Column( type = MySqlTypeConstant.BIGINT, comment = "公司ID")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Long companyId;

    @Column( type = MySqlTypeConstant.BIGINT, comment = "部门ID")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Long departmentId;

}

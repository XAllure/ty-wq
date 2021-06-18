package com.ty.wq.pojo.po.client;

import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.IsNotNull;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.annotation.Unique;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import com.ty.wq.pojo.po.BasePo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;

/**z
 * @author Administrator
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "weChat")
public class WeChat extends BasePo {
    private static final long serialVersionUID = 6673524399742015525L;

    @Unique
    @IsNotNull
    @Column( type = MySqlTypeConstant.VARCHAR, length = 128, comment = "微信id")
    private String weChatId;

    @Column( type = MySqlTypeConstant.VARCHAR, length = 128, comment = "微信号")
    private String weChatNo;

    @Column( type = MySqlTypeConstant.VARCHAR, length = 128, comment = "微信昵称")
    private String weChatNick;

    @Column( type = MySqlTypeConstant.VARCHAR, comment = "高清头像")
    private String headPic;

    @Column( type = MySqlTypeConstant.VARCHAR, comment = "小头像")
    private String smallPic;

    @Column( type = MySqlTypeConstant.INT, comment = "性别")
    private Integer gender;

    @Column( type = MySqlTypeConstant.VARCHAR, length = 128, comment = "国家")
    private String country;

    @Column( type = MySqlTypeConstant.VARCHAR, length = 128, comment = "省份")
    private String province;

    @Column( type = MySqlTypeConstant.VARCHAR, length = 128, comment = "城市")
    private String city;

    @Column( type = MySqlTypeConstant.VARCHAR, comment = "朋友圈个性签名")
    private String signature;

    @Column( type = MySqlTypeConstant.VARCHAR, comment = "朋友圈背景图片")
    private String snsPic;

    @Column( type = MySqlTypeConstant.INT, comment = "是否在线")
    private Integer isOnline;

    @Column( type = MySqlTypeConstant.INT, comment = "是否已登录")
    private Integer isLogin;

    @Column( type = MySqlTypeConstant.VARCHAR, length = 128, comment = "设备ID")
    private String deviceId;

    @Column( type = MySqlTypeConstant.BIGINT, comment = "公司ID")
    private Long companyId;

    @Column( type = MySqlTypeConstant.BIGINT, comment = "部门ID")
    private Long departmentId;

    @Column( type = MySqlTypeConstant.DATETIME, comment = "登录时间")
    private Timestamp loginTime;

}

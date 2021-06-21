package com.ty.wq.pojo.po.client;

import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.IsNotNull;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import com.ty.wq.pojo.po.BasePo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 微信群表
 * @author Administrator
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Table("wechat_room")
public class WechatRoom extends BasePo {
    private static final long serialVersionUID = -8533150900321321982L;

    @Column( type = MySqlTypeConstant.VARCHAR, length = 128, comment = "微信ID")
    private String wechatId;

    @Column( type = MySqlTypeConstant.VARCHAR, length = 128, comment = "群ID")
    private String chatRoomId;

    @Column( type = MySqlTypeConstant.VARCHAR, length = 128, comment = "群名称")
    private String chatRoomName;

    @Column( type = MySqlTypeConstant.INT, comment = "群成员数量")
    private Integer roomMemberCount;

    @Column( type = MySqlTypeConstant.VARCHAR, comment = "群头像")
    private String avatar;

    @Column( type = MySqlTypeConstant.VARCHAR, length = 128, comment = "群主微信ID")
    private String owner;

    @Column( type = MySqlTypeConstant.VARCHAR, length = 128, comment = "群主微信昵称")
    private String ownerNickName;

    @Column( type = MySqlTypeConstant.INT, length = 1, comment = "是否为群主 1是 0否")
    private Integer isOwner;

    @Column( type = MySqlTypeConstant.VARCHAR, comment = "群二维码地址")
    private String url;

    @Column( type = MySqlTypeConstant.INT, length = 11, comment = "限制进群人数")
    private Integer limitCount;

    @IsNotNull
    @Column( type = MySqlTypeConstant.BIGINT, comment = "公司ID")
    private Long companyId;

    @IsNotNull
    @Column( type = MySqlTypeConstant.BIGINT, comment = "部门ID")
    private Long departmentId;

}

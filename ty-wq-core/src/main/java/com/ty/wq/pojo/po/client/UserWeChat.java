package com.ty.wq.pojo.po.client;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.*;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import com.ty.wq.pojo.po.BasePo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Administrator
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "user_weChat")
@TableName("user_weChat")
public class UserWeChat extends BasePo {
    private static final long serialVersionUID = -4375320575653904738L;

    @IsNotNull
    @Column(type = MySqlTypeConstant.BIGINT, comment = "用户id")
    private Long userId;

    @IsNotNull
    @Column(type = MySqlTypeConstant.BIGINT, comment = "微信表中的id字段")
    private Long weChatId;

}

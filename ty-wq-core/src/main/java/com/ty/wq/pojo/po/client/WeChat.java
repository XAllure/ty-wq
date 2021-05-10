package com.ty.wq.pojo.po.client;

import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.IsNotNull;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.annotation.Unique;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import com.ty.wq.pojo.po.BasePo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
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

}

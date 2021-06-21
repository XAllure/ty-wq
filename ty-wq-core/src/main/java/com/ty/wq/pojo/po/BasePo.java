package com.ty.wq.pojo.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.Version;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.IsAutoIncrement;
import com.gitee.sunchenbin.mybatis.actable.annotation.IsKey;
import com.gitee.sunchenbin.mybatis.actable.annotation.IsNotNull;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author Administrator
 */
@Data
@NoArgsConstructor
public class BasePo implements Serializable {

    private static final long serialVersionUID = 4310425468162352984L;

    @TableId(type = IdType.AUTO) // mybatis-plus主键注解
    @IsAutoIncrement // 自增
    @IsKey // actable主键注解
    @Column(comment = "主键id", type = MySqlTypeConstant.BIGINT)
    protected Long id;

    @IsNotNull
    @Column(comment = "状态", length = 1, type = MySqlTypeConstant.INT)
    private Integer status;

    @IsNotNull
    @Column(comment = "逻辑删除", type = MySqlTypeConstant.INT)
    protected Integer deleted;

    @IsNotNull
    @Version
    @Column(comment = "乐观锁", type = MySqlTypeConstant.INT)
    protected Integer version;

    @IsNotNull
    @Column(comment = "创建时间", type = MySqlTypeConstant.DATETIME)
    protected Timestamp createTime;

    @Column(comment = "创建人", type = MySqlTypeConstant.VARCHAR, length = 30)
    protected String createBy;

    @Column(comment = "更新时间", type = MySqlTypeConstant.DATETIME)
    protected Timestamp updateTime;

    @Column(comment = "修改人", type = MySqlTypeConstant.VARCHAR, length = 30)
    protected String updateBy;

}

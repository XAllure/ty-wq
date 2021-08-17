package com.ty.wq.pojo.vo.manager.authority;

import com.ty.wq.pojo.vo.BaseRespVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.sql.Timestamp;

/**
 * @author Administrator
 * @version 1.0
 * @program: ty-wq
 * @description:
 * @date 2021-08-17 03:07:03
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AuthorityRespVo extends BaseRespVo {

    private static final long serialVersionUID = -1L;

    /** 父权限id */
    private Long pid;

    /** 权限名称 */
    private String name;

    /** 权限名 */
    private String permission;

    /** 排序 */
    private String sort;

    /** 状态 */
    private Integer status;

}

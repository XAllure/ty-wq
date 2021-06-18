package com.ty.wq.pojo.vo.client.permission;

import com.ty.wq.pojo.vo.BaseRespVo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Administrator
 * @version 1.0
 * @program: ty-wq
 * @description:
 * @date 2021-06-17 08:15:02
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PermissionRespVo extends BaseRespVo {

    private static final long serialVersionUID = -1L;

    /** 父权限id */
    private Long parentId;

    /** 名称 */
    private String name;

    /** 权限名 */
    private String permission;

    /** 排序 */
    private String sort;

    private Integer status;

}

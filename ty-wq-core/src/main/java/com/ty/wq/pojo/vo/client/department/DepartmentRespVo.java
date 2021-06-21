package com.ty.wq.pojo.vo.client.department;

import com.ty.wq.pojo.vo.BaseRespVo;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * @author Administrator
 * @version 1.0
 * @program: ty-wq
 * @description:
 * @date 2021-06-21 03:40:40
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DepartmentRespVo extends BaseRespVo {

    private static final long serialVersionUID = -1L;

    private Long pid;

    private String name;

    private String phone;

    private Long companyId;

    private String company;

    private Integer status;

}

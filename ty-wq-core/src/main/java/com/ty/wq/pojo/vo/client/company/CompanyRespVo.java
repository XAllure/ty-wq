package com.ty.wq.pojo.vo.client.company;

import com.ty.wq.pojo.vo.BaseReqVo;
import com.ty.wq.pojo.vo.BaseRespVo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;

/**
 * @author Administrator
 * @version 1.0
 * @program: ty-wq
 * @description:
 * @date 2021-06-21 03:40:12
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CompanyRespVo extends BaseRespVo {

    private static final long serialVersionUID = -1L;

    private Long pid;

    private String name;

    private String phone;

    private String email;

    private Integer status;

}

package com.ty.wq.pojo.vo.client.company;

import com.ty.wq.pojo.vo.BaseReqVo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author Administrator
 * @version 1.0
 * @program: ty-wq
 * @description:
 * @date 2021-06-21 03:40:12
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CompanyReqVo extends BaseReqVo {

    private static final long serialVersionUID = -1L;

    private Long pid;

    @NotBlank(message = "公司名称不能为空",groups = {Add.class, Update.class})
    private String name;

    private String phone;

    private String email;

}

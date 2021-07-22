package com.ty.wq.pojo.vo.client.company;

import com.ty.wq.pojo.vo.BaseReqVo;
import com.ty.wq.pojo.vo.BaseRespVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel
public class CompanyRespVo extends BaseRespVo {

    private static final long serialVersionUID = -1L;

    @ApiModelProperty("父ID")
    private Long pid;

    @ApiModelProperty("公司名称")
    private String name;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("状态")
    private Integer status;

}

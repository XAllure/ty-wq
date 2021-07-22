package com.ty.wq.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Administrator
 */
@Data
@ApiModel
public class BaseRespVo implements Serializable {

    private static final long serialVersionUID = 379543016117683112L;

    @ApiModelProperty("主键ID")
    protected Long id;
}

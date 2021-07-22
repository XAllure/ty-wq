package com.ty.wq.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * @author Administrator
 * @description: 页面查询基类
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel
public class BaseSearchVo extends BaseVo {
    private static final long serialVersionUID = 8236469326087369902L;

    @ApiModelProperty("页号")
    private Integer pageNum;

    @ApiModelProperty("页面大小")
    private Integer pageSize;

    @ApiModelProperty("排序字段")
    private String sort;

    @ApiModelProperty("排序方式 asc/desc")
    private String order;
}

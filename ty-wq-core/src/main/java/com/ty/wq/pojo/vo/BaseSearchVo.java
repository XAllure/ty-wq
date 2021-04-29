package com.ty.wq.pojo.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * @author Administrator
 * @description: 页面查询基类
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BaseSearchVo extends BaseVo {
    private static final long serialVersionUID = 8236469326087369902L;

    /** 页号 */
    private Integer pageNum;

    /** 页面大小 */
    private Integer pageSize;

    /** 排序字段 */
    private String sort;

    /** 排序方式 asc/desc */
    private String order;
}

package com.ty.wq.pojo.vo.manager.admin;

import com.ty.wq.anno.SearchCondition;
import com.ty.wq.enums.CompareEnum;
import com.ty.wq.pojo.vo.BaseSearchVo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;

/**
 * @author Administrator
 * @version 1.0
 * @program: ty-wq
 * @description:
 * @date 2021-06-02 09:41:11
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AdminSearchVo extends BaseSearchVo {

    private static final long serialVersionUID = -1L;

    @SearchCondition(compare = CompareEnum.LIKE, filed = "username")
    private String username;

    @SearchCondition(compare = CompareEnum.LIKE, filed = "phone")
    private String phone;

    @SearchCondition(compare = CompareEnum.LIKE, filed = "status")
    private Integer status;

    @SearchCondition(compare = CompareEnum.GE,filed = "create_time")
    private String startTime;

    @SearchCondition(compare = CompareEnum.LE,filed = "create_time")
    private String endTime;

}

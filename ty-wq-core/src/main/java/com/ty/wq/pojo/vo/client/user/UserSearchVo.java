package com.ty.wq.pojo.vo.client.user;

import com.ty.wq.anno.SearchCondition;
import com.ty.wq.enums.CompareEnum;
import com.ty.wq.pojo.vo.BaseSearchVo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Administrator
 * @version 1.0
 * @program: ty-wq
 * @description:
 * @date 2021-04-28 04:15:44
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserSearchVo extends BaseSearchVo {

    private static final long serialVersionUID = -1L;

    @SearchCondition(compare = CompareEnum.LIKE, filed = "username")
    private String username;

    @SearchCondition(compare = CompareEnum.LIKE, filed = "user_nick")
    private String userNick;

    @SearchCondition(compare = CompareEnum.LIKE, filed = "phone")
    private String phone;

    @SearchCondition(compare = CompareEnum.GE,filed = "create_time")
    private String startTime;

    @SearchCondition(compare = CompareEnum.LE,filed = "create_time")
    private String endTime;

}

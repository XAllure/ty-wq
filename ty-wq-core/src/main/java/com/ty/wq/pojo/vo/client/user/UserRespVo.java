package com.ty.wq.pojo.vo.client.user;

import com.ty.wq.pojo.vo.BaseRespVo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Administrator
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserRespVo extends BaseRespVo {

    private static final long serialVersionUID = -7106325894945378008L;

    private String avatar;

    private String username;

    private String userNick;

    private String phone;

    private String email;

    private String company;

    private String department;

    private Long companyId;

    private Long departmentId;

    private String createTime;

    private Integer status;

}

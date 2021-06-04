package com.ty.wq.pojo.vo.manager.admin;

import com.ty.wq.pojo.vo.BaseRespVo;
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
public class AdminRespVo extends BaseRespVo {

    private static final long serialVersionUID = -1L;

    private String avatar;

    private String username;

    private String phone;

    private String email;

    private Integer status;

    private String loginTime;

}

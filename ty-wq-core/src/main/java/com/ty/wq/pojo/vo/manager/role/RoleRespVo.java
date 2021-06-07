package com.ty.wq.pojo.vo.manager.role;

import com.ty.wq.pojo.vo.BaseRespVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.sql.Timestamp;

/**
 * @author Administrator
 * @version 1.0
 * @program: ty-wq
 * @description:
 * @date 2021-06-07 02:02:56
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RoleRespVo extends BaseRespVo {

    private static final long serialVersionUID = -1L;

    private String name;

    private String introduce;

    private Integer status;

}

package com.ty.wq.pojo.vo.manager;

import com.ty.wq.pojo.vo.BaseReqVo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * @author Administrator
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class StatusReqVo extends BaseReqVo {
    private static final long serialVersionUID = 8955105719020496216L;

    @NotNull(message = "状态码不能为空",groups = BaseReqVo.Status.class)
    private Integer status;

}

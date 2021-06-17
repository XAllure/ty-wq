package com.ty.wq.pojo.vo.client.authority;

import com.ty.wq.pojo.vo.BaseReqVo;
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
 * @date 2021-06-17 08:15:02
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AuthorityReqVo extends BaseReqVo {

    private static final long serialVersionUID = -1L;

    /** 父权限id */
    private Long parentId;

    @NotBlank(message = "名称不能为空",groups = {Add.class, Update.class})
    private String name;

    /** 权限名 */
    private String auth;

    /** 链接 */
    private String url;

}

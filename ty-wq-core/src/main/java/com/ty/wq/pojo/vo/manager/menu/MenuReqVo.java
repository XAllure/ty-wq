package com.ty.wq.pojo.vo.manager.menu;

import com.ty.wq.pojo.vo.BaseReqVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

/**
 * @author Administrator
 * @version 1.0
 * @program: ty-wq
 * @description:
 * @date 2021-06-02 09:28:27
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MenuReqVo extends BaseReqVo {
    private static final long serialVersionUID = -1L;

    @NotBlank(message = "菜单名称不能为空",groups = {Add.class, Update.class})
    private String name;

    @NotBlank(message = "菜单图标不能为空",groups = {Add.class, Update.class})
    private String icon;

    /** 父菜单ID */
    private Long pid;

    /** 菜单链接路径 */
    private String path;

    /** 排序 */
    private Integer sort;

}

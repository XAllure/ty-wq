package com.ty.wq.pojo.vo.manager.menu;

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
 * @date 2021-06-02 09:28:27
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MenuRespVo extends BaseRespVo {

    private static final long serialVersionUID = -1L;

    private String name;

    private String icon;

    private Long pid;

    private String path;

    private Integer status;

    private Integer sort;

}

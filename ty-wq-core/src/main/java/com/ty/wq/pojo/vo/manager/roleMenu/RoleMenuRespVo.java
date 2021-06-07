package com.ty.wq.pojo.vo.manager.roleMenu;

import com.ty.wq.pojo.vo.BaseRespVo;
import com.ty.wq.pojo.vo.manager.menu.MenuRespVo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.sql.Timestamp;
import java.util.List;

/**
 * @author Administrator
 * @version 1.0
 * @program: ty-wq
 * @description:
 * @date 2021-06-07 05:48:54
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RoleMenuRespVo extends BaseRespVo {

    private static final long serialVersionUID = 3393098475033688492L;

    List<MenuRespVo> menus;

    List<Long> menuIds;

}

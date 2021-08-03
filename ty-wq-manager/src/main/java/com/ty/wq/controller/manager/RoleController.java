package com.ty.wq.controller.manager;

import com.ty.wq.controller.BaseController;
import com.ty.wq.service.manager.RoleService;
import org.springframework.web.bind.annotation.*;
import com.ty.wq.pojo.po.manager.Role;
import com.ty.wq.pojo.vo.manager.role.RoleReqVo;
import com.ty.wq.pojo.vo.manager.role.RoleRespVo;
import com.ty.wq.pojo.vo.manager.role.RoleSearchVo;
import com.ty.wq.dao.manager.RoleDao;

/**
 * @author Administrator
 * @version 1.0
 * @program: ty-wq
 * @description:
 * @date 2021-06-07 02:02:56
 */
@RestController
@RequestMapping("/role")
public class RoleController extends BaseController<Role, RoleReqVo, RoleRespVo, RoleSearchVo, RoleDao, RoleService> {



}

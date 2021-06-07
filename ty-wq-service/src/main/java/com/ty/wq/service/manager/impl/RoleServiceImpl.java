package com.ty.wq.service.manager.impl;

import com.ty.wq.dao.manager.RoleDao;
import com.ty.wq.pojo.vo.manager.role.RoleSearchVo;
import com.ty.wq.pojo.po.manager.Role;
import com.ty.wq.service.manager.RoleService;
import com.ty.wq.service.base.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 * @version 1.0
 * @program: ty-wq
 * @description:
 * @date 2021-06-07 02:02:56
 */
@Service
public class RoleServiceImpl extends BaseServiceImpl<Role, RoleDao, RoleSearchVo> implements RoleService {
}

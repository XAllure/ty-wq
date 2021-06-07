package com.ty.wq.service.manager.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ty.wq.dao.manager.RoleDao;
import com.ty.wq.enums.StatusEnum;
import com.ty.wq.pojo.vo.manager.role.RoleSearchVo;
import com.ty.wq.pojo.po.manager.Role;
import com.ty.wq.service.manager.RoleService;
import com.ty.wq.service.base.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Administrator
 * @version 1.0
 * @program: ty-wq
 * @description:
 * @date 2021-06-07 02:02:56
 */
@Service
public class RoleServiceImpl extends BaseServiceImpl<Role, RoleDao, RoleSearchVo> implements RoleService {
    @Override
    public List<Role> getAllNormalRole() {
        QueryWrapper<Role> qw = new QueryWrapper<>();
        qw.ne("status", StatusEnum.LOCKED.getCode());
        return findList(qw);
    }
}

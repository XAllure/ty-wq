package com.ty.wq.service.manager;

import com.ty.wq.dao.manager.RoleDao;
import com.ty.wq.pojo.po.manager.Role;
import com.ty.wq.service.base.BaseService;
import com.ty.wq.pojo.vo.manager.role.RoleSearchVo;
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
public interface RoleService extends BaseService<Role, RoleDao, RoleSearchVo> {

    /**
     * 获取所有非禁用角色
     * @return
     */
    List<Role> getAllNormalRole();

}

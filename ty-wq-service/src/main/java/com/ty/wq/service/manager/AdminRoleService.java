package com.ty.wq.service.manager;

import com.ty.wq.dao.manager.AdminRoleDao;
import com.ty.wq.pojo.po.manager.AdminRole;
import com.ty.wq.pojo.vo.manager.adminRole.AdminRoleReqVo;
import com.ty.wq.service.base.BaseService;
import com.ty.wq.pojo.vo.manager.adminRole.AdminRoleSearchVo;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author Administrator
 * @version 1.0
 * @program: ty-wq
 * @description:
 * @date 2021-06-07 03:50:41
 */
@Service
public interface AdminRoleService extends BaseService<AdminRole, AdminRoleDao, AdminRoleSearchVo> {

    /**
     * 根据管理员id获取角色id
     * @param adminId 管理员id
     * @return
     */
    List<Long> getRoleIdsByAdminId(Long adminId);

    /**
     * 修改管理员角色
     * @param adminRoleReqVo
     */
    void updateAdminRoles(AdminRoleReqVo adminRoleReqVo);

}

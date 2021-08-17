package com.ty.wq.service.manager;

import com.ty.wq.dao.manager.RoleAuthorityDao;
import com.ty.wq.pojo.po.manager.RoleAuthority;
import com.ty.wq.pojo.vo.manager.roleAuthority.RoleAuthorityReqVo;
import com.ty.wq.service.base.BaseService;
import com.ty.wq.pojo.vo.manager.roleAuthority.RoleAuthoritySearchVo;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author Administrator
 * @version 1.0
 * @program: ty-wq
 * @description:
 * @date 2021-08-17 05:15:34
 */
@Service
public interface RoleAuthorityService extends BaseService<RoleAuthority, RoleAuthorityDao, RoleAuthoritySearchVo> {

    /**
     * 根据角色id获取权限id集合
     * @param roleId
     * @return
     */
    List<Long> findAuthIdsByRoleId(Long roleId);

    /**
     * 修改角色权限
     * @param reqVo
     */
    void updateRoleAuth(RoleAuthorityReqVo reqVo);

    /**
     * 根据角色ID删除
     * @param roleId
     * @return
     */
    void deleteByRoleId(Long roleId);

}

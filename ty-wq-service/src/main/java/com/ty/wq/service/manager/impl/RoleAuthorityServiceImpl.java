package com.ty.wq.service.manager.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ty.wq.dao.manager.RoleAuthorityDao;
import com.ty.wq.enums.StatusEnum;
import com.ty.wq.pojo.vo.manager.roleAuthority.RoleAuthorityReqVo;
import com.ty.wq.pojo.vo.manager.roleAuthority.RoleAuthoritySearchVo;
import com.ty.wq.pojo.po.manager.RoleAuthority;
import com.ty.wq.service.manager.RoleAuthorityService;
import com.ty.wq.service.base.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * @version 1.0
 * @program: ty-wq
 * @description:
 * @date 2021-08-17 05:15:34
 */
@Service
public class RoleAuthorityServiceImpl extends BaseServiceImpl<RoleAuthority, RoleAuthorityDao, RoleAuthoritySearchVo> implements RoleAuthorityService {

    /**
     * 根据角色id获取权限id集合
     * @param roleId
     * @return
     */
    @Override
    public List<Long> findAuthIdsByRoleId(Long roleId) {
        QueryWrapper<RoleAuthority> qw = new QueryWrapper<>();
        qw.select("authority_id")
                .eq("status", StatusEnum.NORMAL.getCode())
                .eq("role_id", roleId);
        List<Long> authIds = new ArrayList<>();
        for (RoleAuthority authority : findList(qw)) {
            authIds.add(authority.getAuthorityId());
        }
        return authIds;
    }

    /**
     * 修改角色权限
     * @param reqVo
     */
    @Override
    public void updateRoleAuth(RoleAuthorityReqVo reqVo) {
        deleteByRoleId(reqVo.getRoleId());
        if (reqVo.getAuthorityIds().size() > 0 && !reqVo.getAuthorityIds().isEmpty()) {
            List<RoleAuthority> authorities = new ArrayList<>();
            for (Long authId : reqVo.getAuthorityIds()) {
                RoleAuthority roleAuthority = new RoleAuthority();
                roleAuthority.setRoleId(reqVo.getRoleId());
                roleAuthority.setAuthorityId(authId);
                authorities.add(roleAuthority);
            }
            inserts(authorities);
        }
    }

    /**
     * 根据角色ID查询
     * @param roleId
     * @return
     */
    @Override
    public void deleteByRoleId(Long roleId) {
        QueryWrapper<RoleAuthority> qw = new QueryWrapper<>();
        qw.eq("role_id", roleId);
        delete(qw);
    }

}

package com.ty.wq.service.manager.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ty.wq.dao.manager.AuthorityDao;
import com.ty.wq.enums.StatusEnum;
import com.ty.wq.pojo.po.client.Permission;
import com.ty.wq.pojo.vo.manager.authority.AuthoritySearchVo;
import com.ty.wq.pojo.po.manager.Authority;
import com.ty.wq.service.manager.AuthorityService;
import com.ty.wq.service.base.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Administrator
 * @version 1.0
 * @program: ty-wq
 * @description:
 * @date 2021-08-17 03:07:03
 */
@Service
public class AuthorityServiceImpl extends BaseServiceImpl<Authority, AuthorityDao, AuthoritySearchVo> implements AuthorityService {

    /**
     * 查询所有权限
     * @return
     */
    @Override
    public List<Authority> getAll() {
        QueryWrapper<Authority> qw = new QueryWrapper<>();
        qw.eq("status", StatusEnum.NORMAL.getCode()).orderByAsc("sort");
        return findList(qw);
    }

    /**
     * 查询父权限
     * @return
     */
    @Override
    public List<Authority> findParents() {
        QueryWrapper<Authority> qw = new QueryWrapper<>();
        qw.ne("status", StatusEnum.LOCKED.getCode())
                .isNull("permission")
                .or().eq("permission", "");
        return findList(qw);
    }

    /**
     * 根据id集合查询非父权限
     * @param ids
     * @return
     */
    @Override
    public List<Authority> findChildrenByIds(List<Long> ids) {
        QueryWrapper<Authority> qw = new QueryWrapper<>();
        qw.in("id", ids)
                .eq("status", StatusEnum.NORMAL.getCode())
                .isNotNull("permission")
                .ne("permission", "");
        return findList(qw);
    }
}

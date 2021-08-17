package com.ty.wq.service.manager;

import com.ty.wq.dao.manager.AuthorityDao;
import com.ty.wq.pojo.po.client.Permission;
import com.ty.wq.pojo.po.manager.Authority;
import com.ty.wq.service.base.BaseService;
import com.ty.wq.pojo.vo.manager.authority.AuthoritySearchVo;
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
public interface AuthorityService extends BaseService<Authority, AuthorityDao, AuthoritySearchVo> {

    /**
     * 查询所有权限
     * @return
     */
    List<Authority> getAll();

    /**
     * 查询父权限
     * @return
     */
    List<Authority> findParents();

    /**
     * 根据id集合查询非父权限
     * @param ids
     * @return
     */
    List<Authority> findChildrenByIds(List<Long> ids);

}

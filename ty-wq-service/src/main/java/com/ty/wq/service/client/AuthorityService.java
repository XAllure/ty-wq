package com.ty.wq.service.client;

import com.ty.wq.dao.client.AuthorityDao;
import com.ty.wq.pojo.po.client.Authority;
import com.ty.wq.service.base.BaseService;
import com.ty.wq.pojo.vo.client.authority.AuthoritySearchVo;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author Administrator
 * @version 1.0
 * @program: ty-wq
 * @description:
 * @date 2021-06-17 08:15:02
 */
@Service
public interface AuthorityService extends BaseService<Authority, AuthorityDao, AuthoritySearchVo> {

    /**
     * 查询父权限
     * @return
     */
    List<Authority> parentAuthority();

}

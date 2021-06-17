package com.ty.wq.service.client.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ty.wq.dao.client.AuthorityDao;
import com.ty.wq.pojo.vo.client.authority.AuthoritySearchVo;
import com.ty.wq.pojo.po.client.Authority;
import com.ty.wq.service.client.AuthorityService;
import com.ty.wq.service.base.impl.BaseServiceImpl;
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
public class AuthorityServiceImpl extends BaseServiceImpl<Authority, AuthorityDao, AuthoritySearchVo> implements AuthorityService {

    @Override
    public List<Authority> parentAuthority() {
        QueryWrapper<Authority> qw = new QueryWrapper<>();
        qw.isNull("auth").or().eq("auth", "").or().eq("auth", "/");
        return findList(qw);
    }

}

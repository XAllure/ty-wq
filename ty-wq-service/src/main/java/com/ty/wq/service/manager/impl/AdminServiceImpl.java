package com.ty.wq.service.manager.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ty.wq.dao.manager.AdminDao;
import com.ty.wq.pojo.vo.manager.admin.AdminSearchVo;
import com.ty.wq.pojo.po.manager.Admin;
import com.ty.wq.service.manager.AdminService;
import com.ty.wq.service.base.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 * @version 1.0
 * @program: ty-wq
 * @description:
 * @date 2021-06-02 09:41:11
 */
@Service
public class AdminServiceImpl extends BaseServiceImpl<Admin, AdminDao, AdminSearchVo> implements AdminService {

    @Override
    public Admin findByUsername(String username) {
        QueryWrapper<Admin> qw = new QueryWrapper<>();
        //按用户名获取
        qw.eq("username",username);
        return this.findOne(qw);
    }

}

package com.ty.wq.service.manager;

import com.ty.wq.dao.manager.AdminDao;
import com.ty.wq.pojo.po.manager.Admin;
import com.ty.wq.service.base.BaseService;
import com.ty.wq.pojo.vo.manager.admin.AdminSearchVo;
import org.springframework.stereotype.Service;


/**
 * @author Administrator
 * @version 1.0
 * @program: ty-wq
 * @description:
 * @date 2021-06-02 09:41:11
 */
@Service
public interface AdminService extends BaseService<Admin, AdminDao, AdminSearchVo> {

    /**
     * 按用户名查询
     * @param username
     * @return
     */
    Admin findByUsername(String username);

}

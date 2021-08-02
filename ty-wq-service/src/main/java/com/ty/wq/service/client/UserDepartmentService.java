package com.ty.wq.service.client;

import com.ty.wq.dao.client.UserDepartmentDao;
import com.ty.wq.pojo.po.client.UserDepartment;
import com.ty.wq.service.base.BaseService;
import com.ty.wq.pojo.vo.client.userDepartment.UserDepartmentSearchVo;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author Administrator
 * @version 1.0
 * @program: ty-wq
 * @description:
 * @date 2021-06-21 03:42:10
 */
@Service
public interface UserDepartmentService extends BaseService<UserDepartment, UserDepartmentDao, UserDepartmentSearchVo> {

    /**
     * 根据用户ID查询用户部门列表
     * @param userId
     * @return
     */
    List<UserDepartment> findByUserId(Long userId);

}

package com.ty.wq.service.client.impl;

import com.ty.wq.dao.client.UserDepartmentDao;
import com.ty.wq.pojo.vo.client.userDepartment.UserDepartmentSearchVo;
import com.ty.wq.pojo.po.client.UserDepartment;
import com.ty.wq.service.client.UserDepartmentService;
import com.ty.wq.service.base.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 * @version 1.0
 * @program: ty-wq
 * @description:
 * @date 2021-06-21 03:42:10
 */
@Service
public class UserDepartmentServiceImpl extends BaseServiceImpl<UserDepartment, UserDepartmentDao, UserDepartmentSearchVo> implements UserDepartmentService {
}

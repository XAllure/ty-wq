package com.ty.wq.service.client.impl;

import com.ty.wq.dao.client.DepartmentDao;
import com.ty.wq.pojo.vo.client.department.DepartmentSearchVo;
import com.ty.wq.pojo.po.client.Department;
import com.ty.wq.service.client.DepartmentService;
import com.ty.wq.service.base.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 * @version 1.0
 * @program: ty-wq
 * @description:
 * @date 2021-06-21 03:40:40
 */
@Service
public class DepartmentServiceImpl extends BaseServiceImpl<Department, DepartmentDao, DepartmentSearchVo> implements DepartmentService {
}

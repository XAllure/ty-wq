package com.ty.wq.service.client;

import com.ty.wq.dao.client.DepartmentDao;
import com.ty.wq.pojo.po.client.Department;
import com.ty.wq.pojo.vo.client.department.DepartmentRespVo;
import com.ty.wq.service.base.BaseService;
import com.ty.wq.pojo.vo.client.department.DepartmentSearchVo;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author Administrator
 * @version 1.0
 * @program: ty-wq
 * @description:
 * @date 2021-06-21 03:40:40
 */
@Service
public interface DepartmentService extends BaseService<Department, DepartmentDao, DepartmentSearchVo> {

    /**
     * 获取所有部门
     * @return
     */
    List<DepartmentRespVo> getAll();

    /**
     * 按公司id获取部门列表
     * @param companyId
     * @return
     */
    List<Department> getDepartmentByCompanyId(Long companyId);

}

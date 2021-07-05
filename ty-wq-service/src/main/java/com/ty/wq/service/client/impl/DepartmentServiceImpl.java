package com.ty.wq.service.client.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ty.wq.dao.client.DepartmentDao;
import com.ty.wq.enums.StatusEnum;
import com.ty.wq.pojo.po.client.Company;
import com.ty.wq.pojo.vo.client.department.DepartmentRespVo;
import com.ty.wq.pojo.vo.client.department.DepartmentSearchVo;
import com.ty.wq.pojo.po.client.Department;
import com.ty.wq.service.client.CompanyService;
import com.ty.wq.service.client.DepartmentService;
import com.ty.wq.service.base.impl.BaseServiceImpl;
import com.ty.wq.utils.OrikaUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
public class DepartmentServiceImpl extends BaseServiceImpl<Department, DepartmentDao, DepartmentSearchVo> implements DepartmentService {

    @Autowired
    private CompanyService companyService;


    @Override
    public List<DepartmentRespVo> getAll() {
        List<DepartmentRespVo> departmentRespVos = OrikaUtils.converts(findAll(), DepartmentRespVo.class);
        for (DepartmentRespVo vo : departmentRespVos) {
            Company company = companyService.findById(vo.getCompanyId());
            vo.setCompanyName(company.getName());
        }
        return departmentRespVos;
    }

    @Override
    public List<Department> getDepartmentByCompanyId(Long companyId) {

        QueryWrapper<Department> qw = new QueryWrapper<>();
        qw.eq("company_id", companyId).ne("status", StatusEnum.LOCKED.getCode());
        return findList(qw);
    }
}

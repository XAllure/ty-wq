package com.ty.wq.service.client.impl;

import com.ty.wq.dao.client.CompanyDao;
import com.ty.wq.pojo.vo.client.company.CompanySearchVo;
import com.ty.wq.pojo.po.client.Company;
import com.ty.wq.service.client.CompanyService;
import com.ty.wq.service.base.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 * @version 1.0
 * @program: ty-wq
 * @description:
 * @date 2021-06-21 03:40:12
 */
@Service
public class CompanyServiceImpl extends BaseServiceImpl<Company, CompanyDao, CompanySearchVo> implements CompanyService {
}

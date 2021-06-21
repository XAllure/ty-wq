package com.ty.wq.service.client;

import com.ty.wq.dao.client.CompanyDao;
import com.ty.wq.pojo.po.client.Company;
import com.ty.wq.service.base.BaseService;
import com.ty.wq.pojo.vo.client.company.CompanySearchVo;
import org.springframework.stereotype.Service;


/**
 * @author Administrator
 * @version 1.0
 * @program: ty-wq
 * @description:
 * @date 2021-06-21 03:40:12
 */
@Service
public interface CompanyService extends BaseService<Company, CompanyDao, CompanySearchVo> {
}

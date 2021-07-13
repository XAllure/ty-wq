package com.ty.wq.controller;

import com.ty.wq.pojo.po.client.Company;
import com.ty.wq.pojo.vo.Result;
import com.ty.wq.pojo.vo.client.company.CompanyRespVo;
import com.ty.wq.service.client.CompanyService;
import com.ty.wq.utils.OrikaUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    /**
     * 按id获取
     * @param id
     * @return
     */
    @PostMapping("/info/{id}")
    public Result info(@PathVariable Long id) {
        Company company = companyService.findById(id);
        CompanyRespVo vo = OrikaUtils.convert(company, CompanyRespVo.class);
        return Result.success(vo);
    }
}

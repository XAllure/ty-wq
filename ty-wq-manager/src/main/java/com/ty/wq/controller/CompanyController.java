package com.ty.wq.controller;

import com.ty.wq.controller.base.BaseController;
import com.ty.wq.dao.client.CompanyDao;
import com.ty.wq.pojo.po.client.Company;
import com.ty.wq.pojo.vo.Result;
import com.ty.wq.pojo.vo.client.company.CompanyReqVo;
import com.ty.wq.pojo.vo.client.company.CompanyRespVo;
import com.ty.wq.pojo.vo.client.company.CompanySearchVo;
import com.ty.wq.service.client.CompanyService;
import com.ty.wq.utils.OrikaUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("/company")
public class CompanyController extends BaseController<Company, CompanyReqVo, CompanyRespVo, CompanySearchVo, CompanyDao, CompanyService> {

    CompanyController() {
        methodList = false;
    }

    /**
     * 获取所有公司
     * @return
     */
    @PostMapping("/list")
    public Result list() {
        List<CompanyRespVo> respVos = OrikaUtils.converts(service.findAll(), CompanyRespVo.class);
        return Result.success(respVos);
    }

}

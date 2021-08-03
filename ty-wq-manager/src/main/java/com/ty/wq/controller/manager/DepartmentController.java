package com.ty.wq.controller.manager;

import com.ty.wq.controller.BaseController;
import com.ty.wq.dao.client.DepartmentDao;
import com.ty.wq.pojo.po.client.Department;
import com.ty.wq.pojo.vo.Result;
import com.ty.wq.pojo.vo.client.department.DepartmentReqVo;
import com.ty.wq.pojo.vo.client.department.DepartmentRespVo;
import com.ty.wq.pojo.vo.client.department.DepartmentSearchVo;
import com.ty.wq.service.client.DepartmentService;
import com.ty.wq.utils.OrikaUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("/department")
public class DepartmentController extends BaseController<Department, DepartmentReqVo, DepartmentRespVo, DepartmentSearchVo, DepartmentDao, DepartmentService> {

    DepartmentController(){
        methodList = false;
    }

    /**
     * 获取所有部门
     * @return
     */
    @Override
    @GetMapping("/all")
    public Result all() {
        return Result.success(service.getAll());
    }

    /**
     * 按公司id查找部门
     * @param companyId
     * @return
     */
    @PostMapping("/company/{companyId}")
    public Result byCompany(@PathVariable Long companyId) {
        List<DepartmentRespVo> respVos = OrikaUtils.converts(service.getDepartmentByCompanyId(companyId), DepartmentRespVo.class);
        return Result.success(respVos);
    }

}

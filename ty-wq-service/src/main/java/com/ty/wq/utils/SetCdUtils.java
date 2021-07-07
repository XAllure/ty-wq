package com.ty.wq.utils;

import com.ty.wq.service.client.CompanyService;
import com.ty.wq.service.client.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Administrator
 */
@Component
public class SetCdUtils {

    public static SetCdUtils setCdUtils;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private DepartmentService departmentService;



}

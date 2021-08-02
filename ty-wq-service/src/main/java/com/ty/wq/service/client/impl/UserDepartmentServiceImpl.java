package com.ty.wq.service.client.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ty.wq.dao.client.UserDepartmentDao;
import com.ty.wq.enums.StatusEnum;
import com.ty.wq.pojo.vo.client.userDepartment.UserDepartmentSearchVo;
import com.ty.wq.pojo.po.client.UserDepartment;
import com.ty.wq.service.client.UserDepartmentService;
import com.ty.wq.service.base.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Administrator
 * @version 1.0
 * @program: ty-wq
 * @description:
 * @date 2021-06-21 03:42:10
 */
@Service
public class UserDepartmentServiceImpl extends BaseServiceImpl<UserDepartment, UserDepartmentDao, UserDepartmentSearchVo> implements UserDepartmentService {

    /**
     * 根据用户ID查询用户部门列表
     * @param userId
     * @return
     */
    @Override
    public List<UserDepartment> findByUserId(Long userId) {
        QueryWrapper<UserDepartment> qw = new QueryWrapper<>();
        qw.eq("user_id", userId).eq("status", StatusEnum.NORMAL.getCode());
        return findList(qw);
    }

}

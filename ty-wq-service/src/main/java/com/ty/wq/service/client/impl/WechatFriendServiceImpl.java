package com.ty.wq.service.client.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ty.wq.dao.client.WechatFriendDao;
import com.ty.wq.pojo.po.client.Company;
import com.ty.wq.pojo.po.client.Department;
import com.ty.wq.pojo.vo.client.wechat.WechatRespVo;
import com.ty.wq.pojo.vo.client.wechatFriend.WechatFriendRespVo;
import com.ty.wq.pojo.vo.client.wechatFriend.WechatFriendSearchVo;
import com.ty.wq.pojo.po.client.WechatFriend;
import com.ty.wq.service.client.CompanyService;
import com.ty.wq.service.client.DepartmentService;
import com.ty.wq.service.client.WechatFriendService;
import com.ty.wq.service.base.impl.BaseServiceImpl;
import com.ty.wq.utils.OrikaUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author Administrator
 * @version 1.0
 * @program: ty-wq
 * @description:
 * @date 2021-06-21 03:40:57
 */
@Service
public class WechatFriendServiceImpl extends BaseServiceImpl<WechatFriend, WechatFriendDao, WechatFriendSearchVo> implements WechatFriendService {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private DepartmentService departmentService;

    @Override
    public List<WechatFriendRespVo> getFriends(String wechatId) {
        QueryWrapper<WechatFriend> qw = new QueryWrapper<>();
        qw.eq("wechat_id", wechatId);
        List<WechatFriendRespVo> vos = OrikaUtils.converts(findList(qw), WechatFriendRespVo.class);
        vos.forEach(this::setCd);
        return vos;
    }



    /**
     * 为WechatRespVo注入公司名称和部门名称
     * @param vo
     */
    private void setCd(WechatFriendRespVo vo){
        if (Objects.nonNull(vo.getCompanyId())) {
            Company company = companyService.findById(vo.getCompanyId());
            vo.setCompanyName(company.getName());
        }
        if (Objects.nonNull(vo.getDepartmentId())) {
            Department department = departmentService.findById(vo.getDepartmentId());
            vo.setDepartmentName(department.getName());
        }
    }

}

package com.ty.wq.service.client.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ty.wq.dao.client.WechatDao;
import com.ty.wq.pojo.po.client.Company;
import com.ty.wq.pojo.po.client.Department;
import com.ty.wq.pojo.vo.client.wechat.WechatReqVo;
import com.ty.wq.pojo.vo.client.wechat.WechatRespVo;
import com.ty.wq.pojo.vo.client.wechat.WechatSearchVo;
import com.ty.wq.pojo.po.client.Wechat;
import com.ty.wq.service.client.CompanyService;
import com.ty.wq.service.client.DepartmentService;
import com.ty.wq.service.client.WechatService;
import com.ty.wq.service.base.impl.BaseServiceImpl;
import com.ty.wq.utils.OrikaUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author Administrator
 * @version 1.0
 * @program: ty-wq
 * @description:
 * @date 2021-06-15 07:08:40
 */
@Service
public class WechatServiceImpl extends BaseServiceImpl<Wechat, WechatDao, WechatSearchVo> implements WechatService {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private DepartmentService departmentService;

    /**
     * 按id查询
     * @param id
     * @return
     */
    @Override
    public WechatRespVo getById(Long id) {
        WechatRespVo vo = OrikaUtils.convert(findById(id), WechatRespVo.class);
        setCd(vo);
        return vo;
    }

    /**
     * 按微信id查询
     * @param wechatId
     * @return
     */
    @Override
    public Wechat findByWechatId(String wechatId) {
        QueryWrapper<Wechat> qw = new QueryWrapper<>();
        qw.in("wechat_id", wechatId);
        return findOne(qw);
    }

    /**
     * 按微信id查询
     * @param wechatId
     * @return
     */
    @Override
    public WechatRespVo getByWechatId(String wechatId) {
        WechatRespVo vo = OrikaUtils.convert(findByWechatId(wechatId), WechatRespVo.class);
        setCd(vo);
        return vo;
    }

    /**
     * 修改微信信息
     * @param vo
     */
    @Override
    public void update(WechatReqVo vo) {
        Wechat wechat = findByWechatId(vo.getWechatId());
        wechat.setWechatNick(vo.getWechatNick());
        wechat.setHeadPic(vo.getHeadPic());
        wechat.setSmallPic(vo.getSmallPic());
        wechat.setGender(vo.getGender());
        wechat.setCountry(vo.getCountry());
        wechat.setProvince(vo.getProvince());
        wechat.setCity(vo.getCity());
        wechat.setSignature(vo.getSignature());
        wechat.setSnsPic(vo.getSnsPic());
        updateById(wechat);
    }

    /**
     * 为WechatRespVo注入公司名称和部门名称
     * @param vo
     */
    private void setCd(WechatRespVo vo){
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

package com.ty.wq.service.client.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ty.wq.dao.client.WechatDao;
import com.ty.wq.enums.CodeEnum;
import com.ty.wq.enums.StatusEnum;
import com.ty.wq.enums.WechatEnum;
import com.ty.wq.exception.WqException;
import com.ty.wq.pojo.po.client.Company;
import com.ty.wq.pojo.po.client.Department;
import com.ty.wq.pojo.vo.client.wechat.WechatLoginReqVo;
import com.ty.wq.pojo.vo.client.wechat.WechatReqVo;
import com.ty.wq.pojo.vo.client.wechat.WechatRespVo;
import com.ty.wq.pojo.vo.client.wechat.WechatSearchVo;
import com.ty.wq.pojo.po.client.Wechat;
import com.ty.wq.service.client.CompanyService;
import com.ty.wq.service.client.DepartmentService;
import com.ty.wq.service.client.WechatService;
import com.ty.wq.service.base.impl.BaseServiceImpl;
import com.ty.wq.utils.AccessUtils;
import com.ty.wq.utils.OrikaUtils;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
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
     * 登录微信号
     * @param wechatLoginReqVo
     * @return
     */
    @Override
    public List<WechatRespVo> login(WechatLoginReqVo wechatLoginReqVo) {
        List<WechatRespVo> vos = new ArrayList<>();
        StringBuilder msg = new StringBuilder();
        for (String wechatId : wechatLoginReqVo.getWechatIds()) {
            Wechat wechat = findByWechatId(wechatId);
            if (null != wechat) {
                if (wechat.getStatus().equals(StatusEnum.NORMAL.getCode())) {
                    wechat.setIsLogin(WechatEnum.LOGGED_IN.getCode());
                    wechat.setIsOnline(WechatEnum.ONLINE.getCode());
                    wechat.setLoginTime(new Timestamp(System.currentTimeMillis()));
                    updateById(wechat);
                    WechatRespVo vo = OrikaUtils.convert(wechat, WechatRespVo.class);
                    setCd(vo);
                    vos.add(vo);
                } else {
                    msg.append("微信[").append(wechat.getWechatNick()).append("]已被锁定;");
                }
            } else {
                msg.append("微信[").append(wechatId).append("]不存在;");
            }
        }
        if (vos.size() == 0) {
            throw new WqException(CodeEnum.ERROR.getCode(), msg.toString());
        }
        return vos;
    }

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
        Wechat wechat = findByWechatId(wechatId);
        if (wechat == null) {
            throw new WqException(CodeEnum.ERROR_ACCOUNT.getCode(), "无该微信");
        }
        WechatRespVo vo = OrikaUtils.convert(wechat, WechatRespVo.class);
        setCd(vo);
        return vo;
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

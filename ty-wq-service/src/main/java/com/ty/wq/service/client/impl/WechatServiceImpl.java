package com.ty.wq.service.client.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ty.wq.dao.client.WechatDao;
import com.ty.wq.enums.StatusEnum;
import com.ty.wq.pojo.po.client.Company;
import com.ty.wq.pojo.po.client.Department;
import com.ty.wq.pojo.vo.client.wechat.WechatLoginReqVo;
import com.ty.wq.pojo.vo.client.wechat.WechatRespVo;
import com.ty.wq.pojo.vo.client.wechat.WechatSearchVo;
import com.ty.wq.pojo.po.client.Wechat;
import com.ty.wq.service.client.CompanyService;
import com.ty.wq.service.client.DepartmentService;
import com.ty.wq.service.client.WechatService;
import com.ty.wq.service.base.impl.BaseServiceImpl;
import com.ty.wq.utils.AccessUtils;
import com.ty.wq.utils.ChannelUtils;
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

    @Override
    public List<WechatRespVo> login(WechatLoginReqVo wechatLoginReqVo) {
        List<WechatRespVo> vos = new ArrayList<>();
        for (String weChatId : wechatLoginReqVo.getWeChatIds()) {
            Wechat wechat = findByWechatId(weChatId);
            if (null != wechat) {
                wechat.setIsLogin(StatusEnum.LOGGED_IN.getCode());
                wechat.setIsOnline(StatusEnum.ONLINE.getCode());
                wechat.setLoginTime(new Timestamp(System.currentTimeMillis()));
                updateById(wechat);
                WechatRespVo vo = OrikaUtils.convert(wechat, WechatRespVo.class);
                setCd(vo);
                vos.add(vo);
            }
        }
        // 获取用户的channel
        Channel channel = ChannelUtils.userChannel(AccessUtils.userId());
        // 保存微信id与用户channel的对应关系
        ChannelUtils.saveWeChatChannels(channel, vos);
        // 返回已登录的微信
        return vos;
    }

    @Override
    public Wechat findByWechatId(String wechatId) {
        QueryWrapper<Wechat> qw = new QueryWrapper<>();
        qw.in("wechat_id", wechatId);
        return findOne(qw);
    }

    @Override
    public WechatRespVo getByWechatId(String wechatId) {
        WechatRespVo vo = OrikaUtils.convert(findByWechatId(wechatId), WechatRespVo.class);
        setCd(vo);
        return vo;
    }

    @Override
    public WechatRespVo getByWechatNo(String wechatNo) {
        QueryWrapper<Wechat> qw = new QueryWrapper<>();
        qw.in("wechat_no", wechatNo);
        WechatRespVo vo = OrikaUtils.convert(findOne(qw), WechatRespVo.class);
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
package com.ty.wq.handler.websocket;

import com.ty.wq.enums.StatusEnum;
import com.ty.wq.enums.WechatEnum;
import com.ty.wq.pojo.po.client.Company;
import com.ty.wq.pojo.po.client.Department;
import com.ty.wq.pojo.po.client.Wechat;
import com.ty.wq.pojo.vo.BaseReqVo;
import com.ty.wq.pojo.vo.client.wechat.WechatLoginReqVo;
import com.ty.wq.pojo.vo.client.wechat.WechatRespVo;
import com.ty.wq.pojo.vo.netty.Message;
import com.ty.wq.pojo.vo.netty.MsgVo;
import com.ty.wq.service.client.CompanyService;
import com.ty.wq.service.client.DepartmentService;
import com.ty.wq.service.client.WechatService;
import com.ty.wq.utils.*;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

/**
 * @author Administrator
 */
@Service
@Slf4j
public class WechatHandler {

    @Autowired
    private WechatService wechatService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private DepartmentService departmentService;

    /**
     * 登录微信
     * @param channel
     * @param msgVo
     */
    @Async
    public void loginHandler(Channel channel, MsgVo msgVo) {
        WechatLoginReqVo reqVo = MsgUtils.convert(msgVo.getData(), WechatLoginReqVo.class);
        if (!ReqVoUtils.validated(reqVo, channel, msgVo, BaseReqVo.Login.class)) {
            return;
        }
        List<WechatRespVo> vos = new ArrayList<>();
        for (String wechatId : reqVo.getWechatIds()) {
            Wechat wechat = wechatService.findByWechatId(wechatId);
            if (null != wechat) {
                if (wechat.getStatus().equals(StatusEnum.NORMAL.getCode())) {
                    wechat.setIsLogin(WechatEnum.LOGGED_IN.getCode());
                    wechat.setIsOnline(WechatEnum.ONLINE.getCode());
                    wechat.setLoginTime(new Timestamp(System.currentTimeMillis()));
                    wechatService.updateById(wechat);
                    WechatRespVo vo = OrikaUtils.convert(wechat, WechatRespVo.class);
                    setCd(vo);
                    vos.add(vo);
                }
            }
        }
        if (vos.size() == 0) {
            MsgUtils.writeJson(channel, Message.error(msgVo, "无可登录的微信"));
            return;
        }
        // 保存微信id与用户channel的对应关系
        ChannelUtils.saveWeChatChannels(channel, vos);
        msgVo.setData(vos);
        MsgUtils.writeJson(channel, Message.success(msgVo));
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

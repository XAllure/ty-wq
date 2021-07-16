package com.ty.wq.service.client.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ty.wq.dao.client.WechatMessageDao;
import com.ty.wq.pojo.vo.client.wechatMessage.WechatMessageReqVo;
import com.ty.wq.pojo.vo.client.wechatMessage.WechatMessageSearchVo;
import com.ty.wq.pojo.po.client.WechatMessage;
import com.ty.wq.service.client.WechatFriendService;
import com.ty.wq.service.client.WechatMessageService;
import com.ty.wq.service.base.impl.BaseServiceImpl;
import com.ty.wq.service.client.WechatService;
import com.ty.wq.utils.PageUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 * @version 1.0
 * @program: ty-wq
 * @description:
 * @date 2021-06-21 03:41:10
 */
@Service
public class WechatMessageServiceImpl extends BaseServiceImpl<WechatMessage, WechatMessageDao, WechatMessageSearchVo> implements WechatMessageService {

    /**
     * 获取消息记录
     * @param sv
     * @return
     */
    @Override
    public Page<WechatMessage> getRecordMsg(WechatMessageSearchVo sv) {
        QueryWrapper<WechatMessage> qw = new QueryWrapper<>();
        qw.eq("wechat_id", sv.getWechatId());
        // 群的消息
        if (StringUtils.isNotBlank(sv.getRoomWxId())) {
            qw.eq("room_wx_id", sv.getRoomWxId());
        } else {
            qw.eq("wx_id_from", sv.getWxIdFrom())
                    .eq("wx_id_to", sv.getWxIdTo())
                    .or()
                    .eq("wx_id_from", sv.getWxIdTo())
                    .eq("wx_id_to", sv.getWxIdFrom());
        }
        qw.orderByDesc("timestamp");
        return findPage(PageUtils.page(sv), qw);
    }
}

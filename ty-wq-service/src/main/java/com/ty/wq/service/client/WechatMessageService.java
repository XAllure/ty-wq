package com.ty.wq.service.client;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ty.wq.dao.client.WechatMessageDao;
import com.ty.wq.pojo.po.client.WechatMessage;
import com.ty.wq.pojo.vo.Result;
import com.ty.wq.pojo.vo.client.wechatMessage.WechatMessageReqVo;
import com.ty.wq.service.base.BaseService;
import com.ty.wq.pojo.vo.client.wechatMessage.WechatMessageSearchVo;
import org.springframework.stereotype.Service;


/**
 * @author Administrator
 * @version 1.0
 * @program: ty-wq
 * @description:
 * @date 2021-06-21 03:41:10
 */
@Service
public interface WechatMessageService extends BaseService<WechatMessage, WechatMessageDao, WechatMessageSearchVo> {

    /**
     * 获取消息记录
     * @param sv
     * @return
     */
    Page<WechatMessage> getRecordMsg(WechatMessageSearchVo sv);

}

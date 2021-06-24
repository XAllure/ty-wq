package com.ty.wq.service.client;

import com.ty.wq.dao.client.WechatDao;
import com.ty.wq.pojo.po.client.Wechat;
import com.ty.wq.pojo.vo.client.wechat.WechatLoginReqVo;
import com.ty.wq.pojo.vo.client.wechat.WechatReqVo;
import com.ty.wq.pojo.vo.client.wechat.WechatRespVo;
import com.ty.wq.service.base.BaseService;
import com.ty.wq.pojo.vo.client.wechat.WechatSearchVo;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author Administrator
 * @version 1.0
 * @program: ty-wq
 * @description:
 * @date 2021-06-15 07:08:40
 */
@Service
public interface WechatService extends BaseService<Wechat, WechatDao, WechatSearchVo> {


    /**
     * 登录微信号
     * @param wechatLoginReqVo
     * @return
     */
    List<WechatRespVo> login(WechatLoginReqVo wechatLoginReqVo);

    /**
     * 按微信id查询
     * @param wechatId
     * @return
     */
    Wechat findByWechatId(String wechatId);

    /**
     * 按微信id查询
     * @param wechatId
     * @return
     */
    WechatRespVo getByWechatId(String wechatId);

    /**
     * 按微信号查询
     * @param wechatNo
     * @return
     */
    WechatRespVo getByWechatNo(String wechatNo);

}

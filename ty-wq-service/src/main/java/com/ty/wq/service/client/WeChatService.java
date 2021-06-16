package com.ty.wq.service.client;

import com.ty.wq.dao.client.WeChatDao;
import com.ty.wq.pojo.po.client.WeChat;
import com.ty.wq.pojo.vo.client.weChat.WeChatReqVo;
import com.ty.wq.service.base.BaseService;
import com.ty.wq.pojo.vo.client.weChat.WeChatSearchVo;
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
public interface WeChatService extends BaseService<WeChat, WeChatDao, WeChatSearchVo> {

    /**
     * 按微信id查询
     * @param weChatId
     * @return
     */
    WeChat findByWeChatId(String weChatId);

    /**
     * 登录微信号
     * @param weChatReqVo
     * @return
     */
    List<WeChat> login(WeChatReqVo weChatReqVo);

    /**
     * 按id查询
     * @param ids
     * @return
     */
    List<WeChat> findByIds(List<Long> ids);

}

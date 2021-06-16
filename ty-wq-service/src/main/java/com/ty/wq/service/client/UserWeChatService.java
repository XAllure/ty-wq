package com.ty.wq.service.client;

import com.ty.wq.dao.client.UserWeChatDao;
import com.ty.wq.pojo.po.client.UserWeChat;
import com.ty.wq.service.base.BaseService;
import com.ty.wq.pojo.vo.client.userWeChat.UserWeChatSearchVo;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author Administrator
 * @version 1.0
 * @program: ty-wq
 * @description:
 * @date 2021-06-15 10:02:58
 */
@Service
public interface UserWeChatService extends BaseService<UserWeChat, UserWeChatDao, UserWeChatSearchVo> {

    /**
     * 获取用户的所有WeChat的id
     * @param userId
     * @return
     */
    List<Long> getWeChatIds(Long userId);

}

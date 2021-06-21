package com.ty.wq.service.client;

import com.ty.wq.dao.client.WechatRoomDao;
import com.ty.wq.pojo.po.client.WechatRoom;
import com.ty.wq.service.base.BaseService;
import com.ty.wq.pojo.vo.client.wechatRoom.WechatRoomSearchVo;
import org.springframework.stereotype.Service;


/**
 * @author Administrator
 * @version 1.0
 * @program: ty-wq
 * @description:
 * @date 2021-06-21 03:41:23
 */
@Service
public interface WechatRoomService extends BaseService<WechatRoom, WechatRoomDao, WechatRoomSearchVo> {
}

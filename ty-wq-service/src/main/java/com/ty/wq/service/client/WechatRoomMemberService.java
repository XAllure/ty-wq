package com.ty.wq.service.client;

import com.ty.wq.dao.client.WechatRoomMemberDao;
import com.ty.wq.pojo.po.client.WechatRoomMember;
import com.ty.wq.service.base.BaseService;
import com.ty.wq.pojo.vo.client.wechatRoomMember.WechatRoomMemberSearchVo;
import org.springframework.stereotype.Service;


/**
 * @author Administrator
 * @version 1.0
 * @program: ty-wq
 * @description:
 * @date 2021-06-21 03:41:36
 */
@Service
public interface WechatRoomMemberService extends BaseService<WechatRoomMember, WechatRoomMemberDao, WechatRoomMemberSearchVo> {
}

package com.ty.wq.service.client;

import com.ty.wq.dao.client.WechatRoomMemberDao;
import com.ty.wq.pojo.po.client.WechatRoomMember;
import com.ty.wq.pojo.vo.client.wechatRoomMember.WechatRoomMemberReqVo;
import com.ty.wq.service.base.BaseService;
import com.ty.wq.pojo.vo.client.wechatRoomMember.WechatRoomMemberSearchVo;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author Administrator
 * @version 1.0
 * @program: ty-wq
 * @description:
 * @date 2021-06-21 03:41:36
 */
@Service
public interface WechatRoomMemberService extends BaseService<WechatRoomMember, WechatRoomMemberDao, WechatRoomMemberSearchVo> {

    /**
     * 通过微信id和群id查询
     * @param roomId
     * @param wechatId
     * @param chatRoomId
     * @return
     */
    WechatRoomMember getByRoomIdAndWechatIdAndChatRoomId(Long roomId, String wechatId, String chatRoomId);

    /**
     * 获取单个普通群成员信息
     * @param vo
     * @return
     */
    List<WechatRoomMember> getSingleChatRoomMembers(WechatRoomMemberReqVo vo);

    /**
     * 删除群时根据群id删除群成员
     * @param roomId
     * @param chatRoomId
     */
    void deleteByRoomIdAndChatRoomId(Long roomId, String chatRoomId);

    /**
     * 根据微信id列表和群id删除成员
     * @param wechatIds
     * @param chatRoomId
     */
    void deleteByWechatIdsAndChatRoomId(List<String> wechatIds, String chatRoomId);

    /**
     * 修改我在本群的昵称
     * @param vo
     * @return
     */
    void updateChatRoomDisplayName(WechatRoomMemberReqVo vo);

}

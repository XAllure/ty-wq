package com.ty.wq.controller;

import com.ty.wq.pojo.po.client.WechatRoomMember;
import com.ty.wq.pojo.vo.BaseReqVo;
import com.ty.wq.pojo.vo.Result;
import com.ty.wq.pojo.vo.client.wechatRoomMember.WechatRoomMemberReqVo;
import com.ty.wq.pojo.vo.client.wechatRoomMember.WechatRoomMemberRespVo;
import com.ty.wq.service.client.WechatRoomMemberService;
import com.ty.wq.utils.OrikaUtils;
import com.ty.wq.utils.ReqVoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("/wechat/room/member")
public class WechatRoomMemberController {

    @Autowired
    private WechatRoomMemberService wechatRoomMemberService;

    /**
     * 获取单个普通群成员信息
     * @param vo
     * @return
     */
    @PostMapping("/getSingleChatRoomMembers")
    public Result getSingleChatRoomMembers(@RequestBody WechatRoomMemberReqVo vo) {
        ReqVoUtils.validated(vo, BaseReqVo.Info.class);
        WechatRoomMember wechatRoomMember = wechatRoomMemberService.getSingleChatRoomMembers(vo);
        WechatRoomMemberRespVo respVo = OrikaUtils.convert(wechatRoomMember, WechatRoomMemberRespVo.class);
        return Result.success(respVo);
    }

    /**
     * 踢群成员
     * @param vo
     * @return
     */
    @PostMapping("/delChatRoomMembers")
    public Result delChatRoomMembers(@RequestBody WechatRoomMemberReqVo vo) {
        ReqVoUtils.validated(vo, BaseReqVo.Out.class);
        return wechatRoomMemberService.delChatRoomMembers(vo);
    }

}

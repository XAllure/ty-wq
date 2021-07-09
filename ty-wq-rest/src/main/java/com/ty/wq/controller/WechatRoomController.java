package com.ty.wq.controller;

import com.ty.wq.constant.MsgType;
import com.ty.wq.constant.OptionKey;
import com.ty.wq.pojo.po.client.WechatRoom;
import com.ty.wq.pojo.vo.BaseReqVo;
import com.ty.wq.pojo.vo.Result;
import com.ty.wq.pojo.vo.client.wechatMessage.SendMsg;
import com.ty.wq.pojo.vo.client.wechatRoom.WechatRoomReqVo;
import com.ty.wq.pojo.vo.client.wechatRoom.WechatRoomRespVo;
import com.ty.wq.pojo.vo.netty.Option;
import com.ty.wq.service.client.WechatRoomService;
import com.ty.wq.utils.OrikaUtils;
import com.ty.wq.utils.ReqVoUtils;
import com.ty.wq.utils.RouteUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("/wechat/room")
public class WechatRoomController {

    @Autowired
    private WechatRoomService wechatRoomService;

    /**
     * 获取普通群列表
     * @param wechatId
     * @return
     */
    @PostMapping("/getChatRooms/{wechatId}")
    public Result getChatRooms(@Valid @NotBlank(message = "微信id参数错误") @PathVariable String wechatId) {
        List<WechatRoomRespVo> vos = wechatRoomService.getChatRooms(wechatId);
        return Result.success(vos);
    }

    /**
     * 创建群聊
     * @param vo
     * @return
     */
    @PostMapping("/createChatRoom")
    public Result createChatRoom(@RequestBody WechatRoomReqVo vo) {
        ReqVoUtils.validated(vo, BaseReqVo.Add.class);
        SendMsg sMsg = new SendMsg();
        sMsg.setApi(MsgType.CREATE_CHAT_ROOM);
        sMsg.setSendId(vo.getWechatId());
        sMsg.setOption(Option.option()
                .add(OptionKey.WXID_LISTS, vo.getWxidLists())
                .getOption());
        return RouteUtils.send(sMsg);
    }

    /**
     * 退出并删除群
     * @param vo
     * @return
     */
    @PostMapping("/quitDelChatRoom")
    public Result quitDelChatRoom(@RequestBody WechatRoomReqVo vo) {
        ReqVoUtils.validated(vo, BaseReqVo.Delete.class);
        return wechatRoomService.quitDelChatRoom(vo);
    }

}

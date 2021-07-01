package com.ty.wq.controller;

import com.ty.wq.pojo.po.client.WechatRoom;
import com.ty.wq.pojo.vo.BaseReqVo;
import com.ty.wq.pojo.vo.Result;
import com.ty.wq.pojo.vo.client.wechatRoom.WechatRoomReqVo;
import com.ty.wq.service.client.WechatRoomService;
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
@RequestMapping("/wechat/room")
public class WechatRoomController {

    @Autowired
    private WechatRoomService wechatRoomService;

    /**
     * 创建群聊
     * @param wechatRoomReqVo
     * @return
     */
    @PostMapping("/create")
    public Result create(@RequestBody WechatRoomReqVo wechatRoomReqVo) {
        ReqVoUtils.validated(wechatRoomReqVo, BaseReqVo.Add.class);
        WechatRoom wechatRoom = OrikaUtils.convert(wechatRoomReqVo, WechatRoom.class);
        wechatRoomService.insert(wechatRoom);
        return Result.success();
    }

}

package com.ty.wq.controller;

import com.ty.wq.service.client.WechatRoomService;
import org.springframework.beans.factory.annotation.Autowired;
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

}

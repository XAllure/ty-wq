package com.ty.wq.controller;

import com.ty.wq.controller.base.BaseController;
import com.ty.wq.dao.client.WechatDao;
import com.ty.wq.pojo.po.client.Wechat;
import com.ty.wq.pojo.vo.client.wechat.WechatReqVo;
import com.ty.wq.pojo.vo.client.wechat.WechatRespVo;
import com.ty.wq.pojo.vo.client.wechat.WechatSearchVo;
import com.ty.wq.service.client.WechatService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("/wechat")
public class WechatController extends BaseController<Wechat, WechatReqVo, WechatRespVo, WechatSearchVo, WechatDao, WechatService> {
}

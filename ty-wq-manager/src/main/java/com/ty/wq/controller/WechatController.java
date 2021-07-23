package com.ty.wq.controller;

import com.ty.wq.controller.base.BaseController;
import com.ty.wq.dao.client.WechatDao;
import com.ty.wq.enums.WechatEnum;
import com.ty.wq.pojo.po.client.Wechat;
import com.ty.wq.pojo.vo.BaseReqVo;
import com.ty.wq.pojo.vo.Result;
import com.ty.wq.pojo.vo.client.wechat.WechatReqVo;
import com.ty.wq.pojo.vo.client.wechat.WechatRespVo;
import com.ty.wq.pojo.vo.client.wechat.WechatSearchVo;
import com.ty.wq.service.client.WechatService;
import com.ty.wq.utils.OrikaUtils;
import com.ty.wq.utils.ReqVoUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("/wechat")
public class WechatController extends BaseController<Wechat, WechatReqVo, WechatRespVo, WechatSearchVo, WechatDao, WechatService> {

    /**
     * 添加微信
     * @param vo
     * @return
     */
    @Override
    @PostMapping("/add")
    public Result add(@RequestBody WechatReqVo vo) {
        ReqVoUtils.validated(vo, BaseReqVo.Add.class);
        Wechat wechat = OrikaUtils.convert(vo, Wechat.class);
        wechat.setIsOnline(WechatEnum.OFFLINE.getCode());
        wechat.setIsLogin(WechatEnum.LOGGED_OUT.getCode());
        service.insert(wechat);
        return Result.success();
    }


}

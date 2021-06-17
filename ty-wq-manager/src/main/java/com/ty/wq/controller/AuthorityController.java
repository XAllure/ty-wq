package com.ty.wq.controller;

import com.ty.wq.controller.base.BaseController;
import com.ty.wq.dao.client.AuthorityDao;
import com.ty.wq.pojo.po.client.Authority;
import com.ty.wq.pojo.vo.Result;
import com.ty.wq.pojo.vo.client.authority.AuthorityReqVo;
import com.ty.wq.pojo.vo.client.authority.AuthorityRespVo;
import com.ty.wq.pojo.vo.client.authority.AuthoritySearchVo;
import com.ty.wq.service.client.AuthorityService;
import com.ty.wq.utils.OrikaUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("/authority")
public class AuthorityController extends BaseController<Authority, AuthorityReqVo, AuthorityRespVo, AuthoritySearchVo, AuthorityDao, AuthorityService> {

    /**
     * 获取所有权限
     * @return
     */
    @GetMapping("/all")
    public Result all() {
        List<Authority> allAuth = service.findAll();
        List<AuthorityRespVo> respVos = OrikaUtils.converts(allAuth, AuthorityRespVo.class);
        return Result.success(respVos);
    }

    /**
     * 获取父权限
     * @return
     */
    @PostMapping("/parent")
    public Result parent() {
        List<Authority> parentAuth = service.parentAuthority();
        List<AuthorityRespVo> respVos = OrikaUtils.converts(parentAuth, AuthorityRespVo.class);
        return Result.success(respVos);
    }

}

package com.ty.wq.controller.manager;

import com.ty.wq.anno.RePermission;
import com.ty.wq.controller.BaseController;
import com.ty.wq.pojo.po.client.Permission;
import com.ty.wq.pojo.vo.Result;
import com.ty.wq.pojo.vo.client.permission.PermissionRespVo;
import com.ty.wq.service.manager.AuthorityService;
import com.ty.wq.utils.OrikaUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ty.wq.pojo.po.manager.Authority;
import com.ty.wq.pojo.vo.manager.authority.AuthorityReqVo;
import com.ty.wq.pojo.vo.manager.authority.AuthorityRespVo;
import com.ty.wq.pojo.vo.manager.authority.AuthoritySearchVo;
import com.ty.wq.dao.manager.AuthorityDao;

import java.util.List;
// import com.ty.wq.vo.factory.manager.AuthorityFactory;

/**
 * 管理员权限
 * @author Administrator
 * @version 1.0
 * @program: ty-wq
 * @description:
 * @date 2021-08-17 03:07:03
 */
@RestController
@RequestMapping("/authority")
@RePermission(prefix = "authority")
public class AuthorityController extends BaseController<Authority, AuthorityReqVo, AuthorityRespVo, AuthoritySearchVo, AuthorityDao, AuthorityService> {

    AuthorityController() {
        methodList = false;
    }

    /**
     * 获取所有权限
     * @return
     */
    @Override
    @GetMapping("/all")
    @RePermission("list")
    public Result all() {
        List<Authority> authorities = service.getAll();
        List<AuthorityRespVo> respVos = OrikaUtils.converts(authorities, AuthorityRespVo.class);
        return Result.success(respVos);
    }

    /**
     * 获取父权限
     * @return
     */
    @PostMapping("/parent")
    @RePermission("parent")
    public Result parent() {
        List<Authority> parentAuth = service.findParents();
        List<AuthorityRespVo> respVos = OrikaUtils.converts(parentAuth, AuthorityRespVo.class);
        return Result.success(respVos);
    }
}

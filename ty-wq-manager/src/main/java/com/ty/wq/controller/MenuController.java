package com.ty.wq.controller;

import com.ty.wq.controller.base.BaseController;
import com.ty.wq.pojo.vo.Result;
import com.ty.wq.service.manager.MenuService;
import org.springframework.web.bind.annotation.*;
import com.ty.wq.pojo.po.manager.Menu;
import com.ty.wq.pojo.vo.manager.menu.MenuReqVo;
import com.ty.wq.pojo.vo.manager.menu.MenuRespVo;
import com.ty.wq.pojo.vo.manager.menu.MenuSearchVo;
import com.ty.wq.dao.manager.MenuDao;

/**
 * @author Administrator
 * @version 1.0
 * @program: ty-wq
 * @description:
 * @date 2021-06-02 09:28:27
 */
@RestController
@RequestMapping("/menu")
public class MenuController extends BaseController<Menu, MenuReqVo, MenuRespVo, MenuSearchVo, MenuDao, MenuService> {

    /**
     * 获取父菜单
     * @return
     */
    @PostMapping("/parent")
    public Result parent(){
        return Result.success(service.parentMenu());
    }

    /**
     * 获取所有菜单
     * @return
     */
    @Override
    @GetMapping("/all")
    public Result all(){
        return Result.success(service.allMenu());
    }

}

package com.ty.wq.service.manager.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ty.wq.dao.manager.MenuDao;
import com.ty.wq.pojo.vo.manager.menu.MenuRespVo;
import com.ty.wq.pojo.vo.manager.menu.MenuSearchVo;
import com.ty.wq.pojo.po.manager.Menu;
import com.ty.wq.service.manager.MenuService;
import com.ty.wq.service.base.impl.BaseServiceImpl;
import com.ty.wq.utils.OrikaUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Administrator
 * @version 1.0
 * @program: ty-wq
 * @description:
 * @date 2021-06-02 09:28:27
 */
@Service
public class MenuServiceImpl extends BaseServiceImpl<Menu, MenuDao, MenuSearchVo> implements MenuService {

    @Override
    public List<MenuRespVo> parentMenu() {
        QueryWrapper<Menu> qw = new QueryWrapper<>();
        qw.isNull("path").or().eq("path", "").or().eq("path", "/");
        return OrikaUtils.converts(findList(qw), MenuRespVo.class);
    }

    @Override
    public List<MenuRespVo> allMenu() {
        QueryWrapper<Menu> qw = new QueryWrapper<>();
        qw.orderByAsc("sort");
        return OrikaUtils.converts(findList(qw), MenuRespVo.class);
    }

}

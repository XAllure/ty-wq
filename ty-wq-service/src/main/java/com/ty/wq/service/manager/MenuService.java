package com.ty.wq.service.manager;

import com.ty.wq.dao.manager.MenuDao;
import com.ty.wq.pojo.po.manager.Menu;
import com.ty.wq.pojo.vo.manager.menu.MenuRespVo;
import com.ty.wq.service.base.BaseService;
import com.ty.wq.pojo.vo.manager.menu.MenuSearchVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * @author Administrator
 * @version 1.0
 * @program: ty-wq
 * @description:
 * @date 2021-06-02 09:28:27
 */
@Service
public interface MenuService extends BaseService<Menu, MenuDao, MenuSearchVo> {

    /**
     * 查询父菜单
     * @return
     */
    List<MenuRespVo> parentMenu();

    /**
     * 查询所有菜单
     * @return
     */
    List<MenuRespVo> allMenu();

}

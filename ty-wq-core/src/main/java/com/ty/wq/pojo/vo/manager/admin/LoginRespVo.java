package com.ty.wq.pojo.vo.manager.admin;

import com.ty.wq.pojo.vo.manager.menu.MenuRespVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author Administrator
 * @version 1.0
 * @program: ty-qdd
 * @description:
 * @date 2021/1/2 15:50
 */
@Data
@AllArgsConstructor
public class LoginRespVo implements Serializable {
    private static final long serialVersionUID = -5204306546468558784L;

    private String token;

    private String avatar;

    private String username;

    private List<MenuRespVo> menu;
}

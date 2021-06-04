package com.ty.wq.pojo.vo.manager.admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Administrator
 * @version 1.0
 * @program: ty-qdd
 * @description:
 * @date 2021/1/2 15:50
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRespVo implements Serializable {
    private static final long serialVersionUID = -5204306546468558784L;

    private String token;

    private String avatar;

    private String username;
}

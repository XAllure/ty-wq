package com.ty.wq.shiro;

import com.ty.wq.pojo.vo.manager.admin.LoginReqVo;
import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * @author Administrator
 * @version 1.0
 * @program: im
 * @description:
 * @date 2020/8/29 19:15
 */
public class ShiroVerifyUser extends UsernamePasswordToken {

    private static final long serialVersionUID = -8271693044619502818L;

    private String code;

    public String getCode(){
        return this.code;
    }

    public void setCode(String code){
        this.code = code;
    }

    public ShiroVerifyUser(){}

    public ShiroVerifyUser(LoginReqVo loginRequestVo){
        super(loginRequestVo.getUsername(),loginRequestVo.getPassword());
        this.code = loginRequestVo.getCode();
    }
}

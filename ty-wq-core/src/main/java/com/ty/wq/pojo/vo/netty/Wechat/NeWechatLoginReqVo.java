package com.ty.wq.pojo.vo.netty.Wechat;

import com.ty.wq.pojo.vo.BaseReqVo;
import com.ty.wq.pojo.vo.client.wechat.WechatRespVo;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author Administrator
 */
@Data
public class NeWechatLoginReqVo implements Serializable {

    private static final long serialVersionUID = -5672994706479830327L;

    /** 用户ID */
    Long userId;

    /** 要登录的微信 */
    List<WechatRespVo> wechats;

}

package com.ty.wq.pojo.vo.client.weChat;

import com.ty.wq.pojo.vo.BaseReqVo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;
import java.util.List;

/**
 * @author Administrator
 * @version 1.0
 * @program: ty-wq
 * @description:
 * @date 2021-06-15 07:08:40
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class WeChatReqVo extends BaseReqVo {
    private static final long serialVersionUID = -1L;

    @NotBlank(message = "请选择要登录的微信号", groups = {Login.class})
    private List<String> weChatIds;

}

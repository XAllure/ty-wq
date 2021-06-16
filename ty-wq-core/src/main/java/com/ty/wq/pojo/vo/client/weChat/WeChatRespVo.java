package com.ty.wq.pojo.vo.client.weChat;

import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import com.ty.wq.pojo.vo.BaseRespVo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.sql.Timestamp;

/**
 * @author Administrator
 * @version 1.0
 * @program: ty-wq
 * @description:
 * @date 2021-06-15 07:08:40
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class WeChatRespVo extends BaseRespVo {

    private static final long serialVersionUID = -1L;

    private Long id;

    private String weChatId;

    private String weChatNo;

    private String weChatNick;

    private Integer gender;

    private String avatar;

    private String country;

    private String province;

    private String city;

    private Integer isOnline;

    private Integer isLoggedIn;

    private String deviceId;

    private Long cid;

    private Long userId;

    private Integer createGroup;

    private Timestamp loginTime;

}

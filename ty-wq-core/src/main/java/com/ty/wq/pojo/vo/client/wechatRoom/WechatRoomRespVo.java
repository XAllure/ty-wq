package com.ty.wq.pojo.vo.client.wechatRoom;

import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import com.ty.wq.pojo.vo.BaseReqVo;
import com.ty.wq.pojo.vo.BaseRespVo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

/**
 * @author Administrator
 * @version 1.0
 * @program: ty-wq
 * @description:
 * @date 2021-06-21 03:41:23
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class WechatRoomRespVo extends BaseRespVo {

    private static final long serialVersionUID = -1L;

    /** 微信ID */
    private String wechatId;

    /** 微信ID */
    private String chatRoomId;

    /**  */
    private String chatRoomName;

    /** 群成员数量 */
    private Integer roomMemberCount;

    /** 群头像 */
    private String avatar;

    /** 群主微信ID */
    private String owner;

    /** 群主微信昵称 */
    private String ownerNickName;

    /** 是否为群主 是否为群主 1是 0 */
    private Integer isOwner;

    /** 群二维码地址 */
    private String url;

    /** 限制进群人数 */
    private Integer limitCount;

    /** 公司ID */
    private Long companyId;

    /** 部门ID */
    private Long departmentId;

}

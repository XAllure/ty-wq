package com.ty.wq.pojo.vo.client.wechatRoom;

import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import com.ty.wq.pojo.vo.BaseReqVo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author Administrator
 * @version 1.0
 * @program: ty-wq
 * @description:
 * @date 2021-06-21 03:41:23
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class WechatRoomReqVo extends BaseReqVo {

    private static final long serialVersionUID = -1L;

    private String wechatId;

    private String chatRoomId;

    @NotBlank(message = "群名称不能为空", groups = {Add.class, Update.class})
    private String chatRoomName;

    private Integer roomMemberCount;

    @NotBlank(message = "群头像不能为空", groups = {Update.class})
    private String avatar;

    @NotBlank(message = "群主微信ID不能为空", groups = {Add.class, Update.class})
    private String owner;

    @NotBlank(message = "群主微信昵称不能为空", groups = {Add.class, Update.class})
    private String ownerNickName;

    @NotNull(message = "是否为群主", groups = {Add.class, Update.class})
    @Column( type = MySqlTypeConstant.INT, length = 1, comment = "是否为群主 1是 0否")
    private Integer isOwner;

    private String url;

    private Integer limitCount;

    private Long companyId;

    private Long departmentId;

}

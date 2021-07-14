package com.ty.wq.pojo.vo.client.wechatRoom;

import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import com.ty.wq.pojo.vo.BaseReqVo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

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

    @NotBlank(message = "微信ID不能为空", groups = {Get.class, Add.class, Update.class, Delete.class, Out.class})
    private String wechatId;

    @NotBlank(message = "群微信ID不能为空", groups = {Delete.class, Update.class, Out.class})
    private String chatRoomId;

    @NotBlank(message = "群名称不能为空", groups = {Update.class})
    private String chatRoomName;

    private Integer roomMemberCount;

    @NotBlank(message = "群头像不能为空", groups = {Update.class})
    private String avatar;

    @NotBlank(message = "群主微信ID不能为空", groups = {Update.class})
    private String owner;

    @NotBlank(message = "群主微信昵称不能为空", groups = {Update.class})
    private String ownerNickName;

    @NotNull(message = "是否为群主", groups = {Update.class})
    private Integer isOwner;

    /** 群二维码地址 */
    private String url;

    /** 限制进群人数 */
    private Integer limitCount;

    /**公司ID*/
    private Long companyId;

    /** 部门ID */
    private Long departmentId;

    /** 要添加群聊的人员的微信id(由于群聊必须至少3个人, 因此必须包含至少2个好友的wxid) */
    @NotEmpty(message = "请选择要添加群聊的人员，至少2个好友", groups = {Add.class})
    private List<String> wxidLists;

}

package com.ty.wq.pojo.vo.client.wechatRoom;

import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import com.ty.wq.pojo.vo.BaseReqVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel
public class WechatRoomReqVo extends BaseReqVo {

    private static final long serialVersionUID = -1L;

    @ApiModelProperty("微信ID")
    @NotBlank(message = "微信ID不能为空", groups = {Get.class, Add.class, Update.class, Delete.class, Out.class})
    private String wechatId;

    @ApiModelProperty("群微信ID")
    @NotBlank(message = "群微信ID不能为空", groups = {Delete.class, Update.class, Out.class})
    private String chatRoomId;

    @ApiModelProperty("群名称")
    @NotBlank(message = "群名称不能为空", groups = {Update.class})
    private String chatRoomName;

    @ApiModelProperty("群成员数量")
    private Integer roomMemberCount;

    @ApiModelProperty("群头像")
    @NotBlank(message = "群头像不能为空", groups = {Update.class})
    private String avatar;

    @ApiModelProperty("群主微信ID")
    @NotBlank(message = "群主微信ID不能为空", groups = {Update.class})
    private String owner;

    @ApiModelProperty("群主微信昵称")
    @NotBlank(message = "群主微信昵称不能为空", groups = {Update.class})
    private String ownerNickName;

    @ApiModelProperty("是否为群主 1是 0不是")
    private Integer isOwner;

    @ApiModelProperty("群二维码地址")
    private String url;

    @ApiModelProperty("限制进群人数")
    private Integer limitCount;

    @ApiModelProperty("公司ID")
    private Long companyId;

    @ApiModelProperty("部门ID")
    private Long departmentId;

    @ApiModelProperty("要添加群聊的人员的微信id，创建的群成员列表第一个要放上创建者的微信ID")
    @NotEmpty(message = "请选择要添加群聊的人员，至少2个好友", groups = {Add.class})
    private List<String> wxidList;

}

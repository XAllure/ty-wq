package com.ty.wq.pojo.vo.client.wechatRoom;

import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import com.ty.wq.pojo.vo.BaseReqVo;
import com.ty.wq.pojo.vo.BaseRespVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel
public class WechatRoomRespVo extends BaseRespVo {

    private static final long serialVersionUID = -1L;

    @ApiModelProperty("微信ID")
    private String wechatId;

    @ApiModelProperty("群微信ID")
    private String chatRoomId;

    @ApiModelProperty("群名称")
    private String chatRoomName;

    @ApiModelProperty("群成员数量")
    private Integer roomMemberCount;

    @ApiModelProperty("群头像")
    private String avatar;

    @ApiModelProperty("群主微信ID")
    private String owner;

    @ApiModelProperty("群主微信昵称")
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

}

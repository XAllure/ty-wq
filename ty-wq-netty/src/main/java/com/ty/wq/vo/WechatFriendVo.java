package com.ty.wq.vo;

import lombok.Data;

/**
 * 对应微信上报的好友信息
 * @author Administrator
 */
@Data
public class WechatFriendVo {

    /** 微信id */
    private String wxid;

    /** 微信号(有可能为空) */
    private String alias;

    /** 微信昵称 */
    private String nick;

    /** 好友备注 */
    private String remark;

    /** 高清头像的url地址 */
    private String headPic;

    /** 小头像的url地址 */
    private String smallPic;

    /** 性别:1男，2女,0(未知) */
    private Integer sex;

    /** 祖国(可能为空) */
    private String country;

    /** 省份(可能为空) */
    private String province;

    /** 城市(可能为空) */
    private String city;

    /** 朋友圈个性签名 */
    private String signature;

    /** 朋友圈背景图片 */
    private String snspic;

    /** 来源类型 */
    private Integer scene;

    /** 是否成功 1成功,0失败 */
    private Integer status;

    /** 用户v1 */
    private String v1;

    /** 用户v2 */
    private String v2;

}

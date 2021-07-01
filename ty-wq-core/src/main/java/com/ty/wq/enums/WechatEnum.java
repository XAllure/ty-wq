package com.ty.wq.enums;

/**
 * @author Administrator
 */
public enum WechatEnum {

    //枚举类型

    LOGGED_IN(1, "已登录"),
    LOGGED_OUT(0, "已退出"),

    // 微信在线离线
    ONLINE(1, "在线"),
    OFFLINE(0, "离线"),

    // 微信好友来源类型
    SCENE_VERIFY(6, "好友验证"),
    SCENE_ROOM(14, "添加群好友"),
    SCENE_SEARCH(15, "通过查询添加"),
    SCENE_CARD(17, "通过名片添加"),

    // 微信好友状态
    FRIEND_DELETED(0, "已经删除"),
    FRIEND_NORMAL(1, "正常"),
    FRIEND_NEW(2, "新增"),
    FRIEND_REFUSE(3, "拒绝"),

    // 消息类型
    MSG_TYPE_TEXT(1, "文本消息"),
    MSG_TYPE_IMG(3, "图片"),
    MSG_TYPE_FILE(4906, "文件"),
    MSG_TYPE_VIDEO(43, "视频"),
    MSG_TYPE_SOUND(34, "语音"),
    MSG_TYPE_GIF(47, "gif表情"),
    MSG_TYPE_CARD(42, "个人名片"),
    MSG_TYPE_LOCATION(48, "位置消息"),
    MSG_TYPE_URL(4905, "链接消息"),
    MSG_TYPE_SMALL_ROUTINE(4933, "小程序消息"),
    MSG_TYPE_TRANSFER_ACCOUNTS(4920, "转账消息"),

    ;

    private Integer code;

    private String msg;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    WechatEnum(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }

}

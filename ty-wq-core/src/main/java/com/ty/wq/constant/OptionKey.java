package com.ty.wq.constant;

/**
 * @author Administrator
 */
public interface OptionKey {

    /**微信id*/
    String WXID = "wxid";

    /**备注*/
    String REMARK = "remark";

    /**来源类型*/
    String SCENE = "scene";

    /**群微信id*/
    String ROOM_WXID = "roomWxid";

    /**v1*/
    String V1 = "v1";

    /**v2*/
    String V2 = "v2";

    /** 创建群聊的好友的微信id列表字段 */
    String WXID_LISTS = "wxidLists";

    /** 踢群成员字段 */
    String WXID_LIST = "wxidList";

    /** 群名称字段等 */
    String NAME = "name";

    /** 修改我在本群的昵称 */
    String NICK = "nick";

    /** 好友发来的入群链接的地址 */
    String INVITE_URL = "inviteurl";

    /** 消息文本 */
    String TEXT = "text";

    /** At人的wxid */
    String AT_LIST = "atlist";

    /** 图片地址(客户端所在主机的本地图片地址) */
    String IMG_PATH = "imgPath";

    /** 发送文件地址(在线文件需要调用接口下载到本地，再发出) */
    String FILE_PATH = "filePath";

    /** 发送视频地址(在线视频需要调用接口下载到本地，再发出) */
    String VIDEO_PATH = "videoPath";

    /** 发送GIF表情本地图片地址(在线视频需要调用接口下载到本地，再发出) */
    String GIF_PATH = "gifPath";

    /** 发送链接消息需要的标题 */
    String TITLE = "title";

    /** 发送链接消息需要的url链接 */
    String URL = "url";

    /** 发送链接消息需要的描述 */
    String DESC = "desc";

    /** 发送链接消息需要的图片url链接 */
    String PIC = "pic";

    /** 要发送的名片的微信id */
    String WXID_CARD = "wxidCard";

    /** 小程序的消息id */
    String MSG_ID = "msgid";


}

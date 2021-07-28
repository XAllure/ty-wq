package com.ty.wq.handler.websocket;

import com.ty.wq.constant.Action;
import com.ty.wq.handler.report.*;
import com.ty.wq.pojo.vo.client.wechatMessage.ReceiveMsg;
import com.ty.wq.pojo.vo.netty.MsgVo;
import com.ty.wq.utils.OrikaUtils;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * 微信消息回调
 * @author Administrator
 */
@Service
@Slf4j
public class ReceiveMsgHandler {

    @Autowired
    private WechatHandler wechatHandler;

    @Autowired
    private WechatFriendHandler wechatFriendHandler;

    @Autowired
    private WechatChatRoomHandler wechatChatRoomHandler;

    @Autowired
    private WechatChatRoomMemberHandler wechatChatRoomMemberHandler;

    @Autowired
    private WechatMessageHandler wechatMessageHandler;

    @Autowired
    private OtherHandler otherHandler;

    /**
     * 微信消息回调
     * @param msgVo
     */
    @Async
    public void handler(Channel channel, MsgVo msgVo) {
        ReceiveMsg rMsg = OrikaUtils.convert(msgVo.getData(), ReceiveMsg.class);
        // cwxid不为空
        if (StringUtils.isNotBlank(rMsg.getCwxid())) {
            switch (rMsg.getAction()) {
                // 上报当前登录微信详细信息（相当于WeQuick工具登录微信）
                case Action.REPORT_LOGIN_USER: {
                    wechatHandler.loginUserHandler(channel, rMsg);
                    break;
                }
                // 上报退出登录事件
                case Action.REPORT_LOGOUT: {
                    wechatHandler.logoutHandler(rMsg);
                    break;
                }
                // 上报普通好友列表
                case Action.REPORT_CONTACTS: {
                    wechatFriendHandler.friendListHandler(rMsg);
                    break;
                }
                // 上报单个普通好友信息
                case Action.REPORT_SINGLE_CONTACT: {
                    wechatFriendHandler.friendInfoHandler(rMsg);
                    break;
                }
                // 上报任意普通微信反查详细信息
                case Action.REPORT_UPDATE_CONTACT: {
                    wechatFriendHandler.updateContactHandler(rMsg);
                    break;
                }
                // 上报联系人新增通知
                case Action.REPORT_CONTACT_ADD: {
                    wechatFriendHandler.addFriendHandler(rMsg);
                    break;
                }
                // 上报联系人删除通知
                case Action.REPORT_CONTACT_DEL: {
                    wechatFriendHandler.delFriendHandler(rMsg);
                    break;
                }
                // 上报新的加好友请求
                case Action.REPORT_FRIEND_ADD_REQUEST: {
                    wechatFriendHandler.friendAddRequestHandler(rMsg);
                    break;
                }
                // 上报加好友指令返回状态
                case Action.REPORT_ADD_FRIEND_MESSAGE: {
                    wechatFriendHandler.addFriendMessageHandler(rMsg);
                    break;
                }
                // 上报通过手机号/微信号/QQ号查询任意微信号信息
                case Action.REPORT_SEARCH_CONTACT: {
                    wechatFriendHandler.searchContactHandler(rMsg);
                    break;
                }
                // 上报普通群列表
                case Action.REPORT_CHAT_ROOMS: {
                    wechatChatRoomHandler.chatRoomsHandler(rMsg);
                    break;
                }
                // 上报单个普通群成员信息
                case Action.REPORT_SINGLE_CHAT_ROOM_MEMBERS: {
                    wechatChatRoomMemberHandler.singleChatRoomMembersHandler(rMsg);
                    break;
                }
                // 上报群成员新增通知
                case Action.REPORT_CHAT_ROOM_MEMBER_ADD: {
                    wechatChatRoomHandler.chatRoomMemberAddHandler(rMsg);
                    break;
                }
                // 上报群成员删除通知
                case Action.REPORT_CHAT_ROOM_MEMBER_DEL: {
                    wechatChatRoomHandler.chatRoomMemberDelHandler(rMsg);
                    break;
                }
                // 上报检测到的新群通知
                case Action.REPORT_NEW_CHAT_ROOM: {
                    wechatChatRoomHandler.newChatRoomHandler(rMsg);
                    break;
                }
                // 上报退群或被踢通知
                case Action.REPORT_CHAT_ROOM_QUIT: {
                    wechatChatRoomHandler.chatRoomQuitHandler(rMsg);
                    break;
                }
                // 上报文本消息
                case Action.REPORT_TEXT_MESSAGE: {
                    wechatMessageHandler.textMessageHandler(rMsg);
                    break;
                }
                // 上报图片消息
                case Action.REPORT_PIC_MESSAGE: {
                    wechatMessageHandler.picMessageHandler(rMsg);
                    break;
                }
                // 上报文件消息
                case Action.REPORT_FILE_MESSAGE: {
                    wechatMessageHandler.fileMessageHandler(rMsg);
                    break;
                }
                // 上报视频消息
                case Action.REPORT_VIDEO_MESSAGE: {
                    wechatMessageHandler.videoMessageHandler(rMsg);
                    break;
                }
                // 上报语音消息
                case Action.REPORT_VOICE_MESSAGE: {
                    wechatMessageHandler.voiceMessageHandler(rMsg);
                    break;
                }
                // 上报GIF表情消息/上报个人名片消息/上报位置消息/上报链接消息/上报小程序消息/上报转账消息
                case Action.REPORT_GIF_MESSAGE:
                case Action.REPORT_CARD_MESSAGE:
                case Action.REPORT_LOCATION_MESSAGE:
                case Action.REPORT_LINK_MESSAGE:
                case Action.REPORT_MINI_MESSAGE:
                case Action.REPORT_TRANSFER_MESSAGE:{
                    wechatMessageHandler.gifOrCardOrLocationOrLinkOrMiniOrTransferMessageHandler(rMsg);
                    break;
                }
                // 上报系统消息/上报其他消息/上报其他接收应用未知消息
                case Action.REPORT_SYSTEM_MESSAGE:
                case Action.REPORT_OTHER_MESSAGE:
                case Action.REPORT_OTHER_APP_MESSAGE: {
                    wechatMessageHandler.systemOrOtherOrOtherAppMessageHandler(rMsg);
                }


                // 上报当前聊天对象改变
                case Action.REPORT_TALKER_CHANGE: {
                    otherHandler.talkerChangeHandler(rMsg);
                    break;
                }
                // 上报URL访问状态
                case Action.REPORT_CHECK_URL_STATUS: {
                    otherHandler.checkUrlStatusHandler(rMsg);
                    break;
                }
                // 上报上传客户端文件到服务端结果
                case Action.RES_UPLOAD_FILE: {
                    otherHandler.resUploadFileHandler(rMsg);
                    break;
                }
                default: {
                    break;
                }
            }
        }
    }

}

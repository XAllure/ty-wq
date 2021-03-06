package com.ty.wq.pojo.po.client;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.annotation.Unique;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import com.ty.wq.pojo.po.BasePo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 微信消息表
 * @author Administrator
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Table("wechat_message")
@TableName("wechat_message")
public class WechatMessage extends BasePo {

    private static final long serialVersionUID = -6028864083651512393L;

    @Column( type = MySqlTypeConstant.VARCHAR, length = 128, comment = "微信id")
    private String wechatId;

    @Column( type = MySqlTypeConstant.INT, comment = "消息类型 1-文本 3-图片 4906-文件 43-视频 34-语音 47-gif表情 42-个人名片 48-位置消息 4905-链接消息 4933-小程序消息 4920-转账消息")
    private Integer msgType;

    @Column( type = MySqlTypeConstant.INT, length = 1, comment = "是否发送 1-发送 0-接受")
    private Integer isSend;

    @Column( type = MySqlTypeConstant.INT, length = 1, comment = "是否pc端发出 1-是 0-否")
    private Integer isPc;

    @Column( type = MySqlTypeConstant.VARCHAR, length = 64, comment = "消息ID")
    private String msgId;

    @Unique
    @Column( type = MySqlTypeConstant.VARCHAR, length = 64, comment = "服务端消息ID")
    private String msgSvrId;

    @Column( type = MySqlTypeConstant.VARCHAR, length = 128, comment = "群ID 聊天消息发生在哪个群(如果是私聊则为空)")
    private String roomWxId;

    @Column( type = MySqlTypeConstant.VARCHAR, length = 128, comment = "微信发送者")
    private String wxIdFrom;

    @Column( type = MySqlTypeConstant.VARCHAR, length = 128, comment = "微信接收者 如果发往群的消息,这个值就是群的wxid 如果是别人私聊给自己的消息,这里就是自己的微信号")
    private String wxIdTo;

    @Column( type = MySqlTypeConstant.TEXT, comment = "消息内容 存json数据")
    private String content;

    @Column( type = MySqlTypeConstant.BIGINT, comment = "公司ID")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Long companyId;

    @Column( type = MySqlTypeConstant.BIGINT, comment = "部门ID")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Long departmentId;

    @Column( type = MySqlTypeConstant.BIGINT, comment = "消息时间戳")
    private Long timestamp;

}

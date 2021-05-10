package com.ty.wq.pojo.vo.netty;

import com.ty.wq.enums.CodeEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Administrator
 */
@Data
public class Message implements Serializable {

    private static final long serialVersionUID = -4946511696855662893L;

    /** 消息类型 */
    private String type;

    /** 状态码 */
    private Integer code;

    /** 返回消息 */
    private String msg;

    /** 返回数据 */
    private Object data;

    /**
     * 返回 Message
     * @param data 数据
     * @return
     */
    private static Message reMessage(Object data) {
        Message message = new Message();
        if (data instanceof CodeEnum){
            CodeEnum codeEnum = (CodeEnum)data;
            message.setCode(codeEnum.getCode());
            message.setMsg(codeEnum.getMsg());
            return message;
        }
        message.setCode(CodeEnum.SUCCESS.getCode());
        message.setMsg(CodeEnum.SUCCESS.getMsg());
        message.setData(data);
        return message;
    }

    /**
     * 成功回执 自定义 type 和 data 字段
     * @param codeEnum
     * @return
     */
    public static Message success(CodeEnum codeEnum) {
        return reMessage(codeEnum);
    }

    /**
     * 成功回执
     * @param msgVo 消息体
     * @param data 数据
     * @return 返回成功信息
     */
    public static Message success(MsgVo msgVo, Object data){
        Message message = reMessage(data);
        message.setType(msgVo.getType());
        return message;
    }

    /**
     * 成功回执
     * @param type 消息类型
     * @param data 数据
     * @return
     */
    public static Message success(String type, Object data){
        Message message = reMessage(data);
        message.setType(type);
        return message;
    }

    /**
     * 成功回执
     * @param msgVo 消息体
     * @return
     */
    public static Message success(MsgVo msgVo){
        return success(msgVo, null);
    }

    /**
     * 成功回执
     * @param type 消息类型
     * @return
     */
    public static Message success(String type){
        return success(type, null);
    }

    /**
     * 错误回执
     * @param codeEnum 枚举信息
     * @return 返回错误信息
     */
    public static Message error(MsgVo msgVo, CodeEnum codeEnum){
        return error(msgVo.getType(), codeEnum.getCode(), codeEnum.getMsg());
    }

    /**
     * 错误回执
     * @param codeEnum 枚举信息
     * @return 返回错误信息
     */
    public static Message error(String type, CodeEnum codeEnum){
        return error(type, codeEnum.getCode(), codeEnum.getMsg());
    }

    /**
     * 错误回执
     * @param code 错误码
     * @param msg 错误消息
     * @return 返回错误信息
     */
    public static Message error(String type, Integer code, String msg){
        Message message = new Message();
        message.setType(type);
        message.setCode(code);
        message.setMsg(msg);
        return message;
    }

    /**
     * 错误回执
     * @param msgVo 消息体
     * @return 返回错误信息
     */
    public static Message error(MsgVo msgVo){
        return error(msgVo, CodeEnum.ERROR);
    }

    /**
     * 错误回执
     * @param type 消息类型
     * @return 返回错误信息
     */
    public static Message error(String type){
        return error(type, CodeEnum.ERROR);
    }

    /**
     * 错误回执
     * @param msgVo 消息体
     * @param msg 错误消息
     * @return 返回错误信息
     */
    public static Message error(MsgVo msgVo, String msg){
        return error(msgVo.getType(), CodeEnum.ERROR.getCode(), msg);
    }

    /**
     * 错误回执
     * @param type 消息类型
     * @param msg 错误消息
     * @return 返回错误信息
     */
    public static Message error(String type, String msg){
        return error(type, CodeEnum.ERROR.getCode(), msg);
    }

}

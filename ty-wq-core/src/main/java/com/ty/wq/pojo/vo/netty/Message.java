package com.ty.wq.pojo.vo.netty;

import com.ty.wq.enums.ResultEnum;
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
    private static Message msgRespVo(Object data) {
        Message result = new Message();
        if (data instanceof ResultEnum){
            ResultEnum resultEnum = (ResultEnum)data;
            result.setCode(resultEnum.getCode());
            result.setMsg(resultEnum.getMsg());
        }else {
            result.setCode(ResultEnum.SUCCESS.getCode());
            result.setMsg(ResultEnum.SUCCESS.getMsg());
            result.setData(data);
        }
        return result;
    }

    /**
     * 成功回执 自定义 type 和 data 字段
     * @param resultEnum
     * @return
     */
    public static Message success(ResultEnum resultEnum) {
        return msgRespVo(resultEnum);
    }

    /**
     * 成功回执
     * @param msgVo 消息体
     * @param data 数据
     * @return 返回成功信息
     */
    public static Message success(MsgVo msgVo, Object data){
        Message result = msgRespVo(data);
        result.setType(msgVo.getType());
        return result;
    }

    /**
     * 成功回执
     * @param type 消息类型
     * @param data 数据
     * @return
     */
    public static Message success(String type, Object data){
        Message result = msgRespVo(data);
        result.setType(type);
        return result;
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
     * @param resultEnum 枚举信息
     * @return 返回错误信息
     */
    public static Message error(MsgVo msgVo, ResultEnum resultEnum){
        return error(msgVo.getType(), resultEnum.getCode(),resultEnum.getMsg());
    }

    /**
     * 错误回执
     * @param resultEnum 枚举信息
     * @return 返回错误信息
     */
    public static Message error(String type, ResultEnum resultEnum){
        return error(type, resultEnum.getCode(),resultEnum.getMsg());
    }

    /**
     * 错误回执
     * @param code 错误码
     * @param msg 错误消息
     * @return 返回错误信息
     */
    public static Message error(String type, Integer code, String msg){
        Message result = new Message();
        result.setType(type);
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

    /**
     * 错误回执
     * @param msgVo 消息体
     * @return 返回错误信息
     */
    public static Message error(MsgVo msgVo){
        return error(msgVo, ResultEnum.ERROR);
    }

    /**
     * 错误回执
     * @param type 消息类型
     * @return 返回错误信息
     */
    public static Message error(String type){
        return error(type, ResultEnum.ERROR);
    }

    /**
     * 错误回执
     * @param msgVo 消息体
     * @param msg 错误消息
     * @return 返回错误信息
     */
    public static Message error(MsgVo msgVo, String msg){
        return error(msgVo.getType(), ResultEnum.ERROR.getCode(), msg);
    }

    /**
     * 错误回执
     * @param type 消息类型
     * @param msg 错误消息
     * @return 返回错误信息
     */
    public static Message error(String type, String msg){
        return error(type, ResultEnum.ERROR.getCode(), msg);
    }

}

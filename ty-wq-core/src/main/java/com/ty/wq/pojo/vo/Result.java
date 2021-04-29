package com.ty.wq.pojo.vo;

import com.ty.wq.enums.ResultEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Administrator
 */
@Data
public class Result implements Serializable {
    private static final long serialVersionUID = 3558270675228629494L;

    /** 错误码 */
    private Integer code;

    /** 错误信息 */
    private String msg;

    /** 数据 */
    private Object data;

    /**
     * 成功回执
     * @param data 数据
     * @return 返回成功信息
     */
    public static Result success(Object data){
        Result result = new Result();
        if (data instanceof ResultEnum){
            ResultEnum errorCodeEnum = (ResultEnum)data;
            result.setCode(errorCodeEnum.getCode());
            result.setMsg(errorCodeEnum.getMsg());
        }else {
            result.setCode(ResultEnum.SUCCESS.getCode());
            result.setMsg(ResultEnum.SUCCESS.getMsg());
            result.setData(data);
        }
        return result;
    }

    /**
     * 成功回执
     * @return 返回成功信息
     */
    public static Result success(){
        return success(null);
    }

    /**
     * 错误回执
     * @param resultEnum 枚举信息
     * @return 返回错误信息
     */
    public static Result error(ResultEnum resultEnum){
        return error(resultEnum.getCode(),resultEnum.getMsg());
    }

    /**
     * 错误回执
     * @param code 错误码
     * @param msg 错误消息
     * @return 返回错误信息
     */
    public static Result error(Integer code, String msg){
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

    /**
     * 错误回执
     * @return 返回错误信息
     */
    public static Result error(){
        return error(ResultEnum.ERROR);
    }


    /**
     *
     * @param msg 错误消息
     * @return 返回错误信息
     */
    public static Result error(String msg){
        return error(ResultEnum.ERROR.getCode(), msg);
    }
}

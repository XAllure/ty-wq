package com.ty.wq.pojo.vo;

import com.ty.wq.enums.CodeEnum;
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
        if (data instanceof CodeEnum){
            CodeEnum codeEnum = (CodeEnum)data;
            result.setCode(codeEnum.getCode());
            result.setMsg(codeEnum.getMsg());
        }else {
            result.setCode(CodeEnum.SUCCESS.getCode());
            result.setMsg(CodeEnum.SUCCESS.getMsg());
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
     * @param codeEnum 枚举信息
     * @return 返回错误信息
     */
    public static Result error(CodeEnum codeEnum){
        return error(codeEnum.getCode(), codeEnum.getMsg());
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
        return error(CodeEnum.ERROR);
    }


    /**
     *
     * @param msg 错误消息
     * @return 返回错误信息
     */
    public static Result error(String msg){
        return error(CodeEnum.ERROR.getCode(), msg);
    }
}

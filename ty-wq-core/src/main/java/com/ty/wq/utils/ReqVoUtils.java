package com.ty.wq.utils;

import com.ty.wq.enums.CodeEnum;
import com.ty.wq.exception.WqException;
import com.ty.wq.pojo.vo.netty.Message;
import com.ty.wq.pojo.vo.netty.MsgVo;
import io.netty.channel.Channel;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import java.util.Set;

/**
 * @author Administrator
 */
public class ReqVoUtils {

    public static <Q> void validated(@Validated Q q, Class<?>... groups){
        Set<ConstraintViolation<Q>> validateSet = Validation.buildDefaultValidatorFactory().getValidator().validate(q, groups);
        if (!CollectionUtils.isEmpty(validateSet)){
            for (ConstraintViolation<Q> constraintViolation : validateSet){
                if (StringUtils.isNotBlank(constraintViolation.getMessage())){
                    throw new WqException(CodeEnum.ERROR_PARAMETER.getCode(),constraintViolation.getMessage());
                }
            }
        }
    }

    public static <Q> boolean validated(@Validated Q q, Channel channel, MsgVo msgVo, Class<?>... groups){
        Set<ConstraintViolation<Q>> validateSet = Validation.buildDefaultValidatorFactory().getValidator().validate(q, groups);
        if (!CollectionUtils.isEmpty(validateSet)){
            for (ConstraintViolation<Q> constraintViolation : validateSet){
                if (StringUtils.isNotBlank(constraintViolation.getMessage())){
                    MsgUtils.writeJson(channel, Message.error(msgVo.getType(), CodeEnum.ERROR_PARAMETER.getCode(), constraintViolation.getMessage()));
                    return false;
                }
            }
        }
        return true;
    }

}

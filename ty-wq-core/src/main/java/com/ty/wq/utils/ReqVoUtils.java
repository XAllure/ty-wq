package com.ty.wq.utils;

import com.ty.wq.enums.ResultEnum;
import com.ty.wq.exception.WqException;
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
                    throw new WqException(ResultEnum.ERROR_PARAMETER.getCode(),constraintViolation.getMessage());
                }
            }
        }
    }

}

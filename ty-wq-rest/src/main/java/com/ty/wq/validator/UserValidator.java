package com.ty.wq.validator;


import com.ty.wq.pojo.vo.client.user.UserReqVo;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author Administrator
 * @version 1.0
 * @program: ty-wq
 * @description:
 * @date 2021-04-28 04:15:44
 */
@Component
public class UserValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.isAssignableFrom(UserReqVo.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserReqVo reqVo = (UserReqVo) target;
    }
}

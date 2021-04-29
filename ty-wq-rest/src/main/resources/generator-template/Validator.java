package ${packageName};


import ${voPackageName}.${name}ReqVo;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author Administrator
 * @version 1.0
 * @program: ty-wq
 * @description:
 * @date ${time}
 */
@Component
public class ${name}Validator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.isAssignableFrom(${name}ReqVo.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ${name}ReqVo reqVo = (${name}ReqVo) target;
    }
}

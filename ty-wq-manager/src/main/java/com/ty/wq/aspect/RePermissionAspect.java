package com.ty.wq.aspect;

import com.ty.wq.enums.CodeEnum;
import com.ty.wq.exception.WqException;
import com.ty.wq.anno.RePermission;
import com.ty.wq.shiro.ShiroUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author Administrator
 * @version 1.0
 * @program: ty-qdd
 * @description: 权限
 * @date 2020/6/27 15:47
 */
@Order(5)
@Aspect
@Component
@Slf4j
public class RePermissionAspect {

    @Pointcut(value = "@annotation(com.ty.wq.anno.RePermission)")
    private void pointcut() {}

    @Before("pointcut()  && @annotation(rePermission)")
    public void doBefore(JoinPoint joinPoint, RePermission rePermission) {
        Object target = joinPoint.getTarget();
        RePermission rp = target.getClass().getAnnotation(RePermission.class);
        String[] permissions = rePermission.value();
        // 类上没有注解，直接判断方法上的权限
        if (rp == null || StringUtils.isBlank(rp.prefix())) {
            if (!ShiroUtils.isPermittedAll(permissions)){
                throw new WqException(CodeEnum.NO_AUTHORITY);
            }
        }else { // 类上有注解
            String prefix = rp.prefix();
            String[] newPermissions = new String[permissions.length];
            for (int i = 0; i < permissions.length; i++){
                newPermissions[i] = prefix + ":" + permissions[i];
            }
            if (!ShiroUtils.isPermittedAll(newPermissions)){
                throw new WqException(CodeEnum.NO_AUTHORITY);
            }
        }
    }
}

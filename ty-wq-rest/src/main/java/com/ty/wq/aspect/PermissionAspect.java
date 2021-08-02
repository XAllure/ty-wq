package com.ty.wq.aspect;

import com.ty.wq.anno.Permission;
import com.ty.wq.enums.CodeEnum;
import com.ty.wq.exception.WqException;
import com.ty.wq.utils.PermissionUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 权限
 * @author Administrator
 */
@Order(5)
@Aspect
@Component
@Slf4j
public class PermissionAspect {

    @Pointcut(value = "@annotation(com.ty.wq.anno.Permission)")
    private void pointcut() {}

    @Before(value = "pointcut() && @annotation(permission)")
    public void doBefore(JoinPoint joinPoint, Permission permission) {
        Object target = joinPoint.getTarget();
        Permission wp = target.getClass().getAnnotation(Permission.class);
        List<String> perms = Arrays.asList(permission.value());
        // 类上没有注解，直接判断方法上的权限
        if (wp == null || StringUtils.isBlank(wp.prefix())) {
            if (!PermissionUtils.isPermitted(perms)) {
                throw new WqException(CodeEnum.NO_AUTHORITY);
            }
        } else {
            // 类上有注解
            String prefix = wp.prefix();
            List<String> permissions = new ArrayList<>();
            for (String perm : perms) {
                permissions.add(prefix.concat(":").concat(perm));
            }
            if (!PermissionUtils.isPermitted(permissions)) {
                throw new WqException(CodeEnum.NO_AUTHORITY);
            }
        }
    }

}

package com.ty.wq.aspect;

import com.ty.wq.anno.VxPermission;
import com.ty.wq.enums.CodeEnum;
import com.ty.wq.exception.WqException;
import com.ty.wq.utils.VxPermissionUtils;
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
public class VxPermissionAspect {

    @Pointcut(value = "@annotation(com.ty.wq.anno.VxPermission)")
    private void pointcut() {}

    @Before(value = "pointcut() && @annotation(vxPermission)")
    public void doBefore(JoinPoint joinPoint, VxPermission vxPermission) {
        Object target = joinPoint.getTarget();
        VxPermission wp = target.getClass().getAnnotation(VxPermission.class);
        List<String> perms = Arrays.asList(vxPermission.value());
        // 类上没有注解，直接判断方法上的权限
        if (wp == null || StringUtils.isBlank(wp.prefix())) {
            if (VxPermissionUtils.notPermitted(perms)) {
                throw new WqException(CodeEnum.NO_AUTHORITY);
            }
        } else {
            // 类上有注解
            String prefix = wp.prefix();
            List<String> permissions = new ArrayList<>();
            for (String perm : perms) {
                permissions.add(prefix.concat(":").concat(perm));
            }
            if (VxPermissionUtils.notPermitted(permissions)) {
                throw new WqException(CodeEnum.NO_AUTHORITY);
            }
        }
    }

}

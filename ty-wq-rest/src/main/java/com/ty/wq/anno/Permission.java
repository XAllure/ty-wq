package com.ty.wq.anno;

import java.lang.annotation.*;

/**
 * 微信权限表
 * @author Administrator
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Permission {

    String[] value() default {};

    String prefix() default "";

}

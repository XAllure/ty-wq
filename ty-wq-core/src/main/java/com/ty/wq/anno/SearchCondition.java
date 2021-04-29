package com.ty.wq.anno;

import com.ty.wq.enums.CompareEnum;

import java.lang.annotation.*;

/**
 * @author Administrator
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface SearchCondition {
    /**
     * 比较符
     * @return
     */
    CompareEnum compare() default CompareEnum.EQUAL;

    /**
     * 转换后的类型
     * @return
     */
    Class clazz() default String.class;

    /**
     * 字段名称
     * @return
     */
    String filed() default "";
}

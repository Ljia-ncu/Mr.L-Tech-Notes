package com.mrl.springboot.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @AnnotationName: TestException
 * @Description
 * @Author Mr.L
 * @Date 2020/12/10 11:08
 * @Version 1.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PreAuthorize {
    String operation() default "";

    String exception() default "403";
}

package com.mrl.springboot.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Service;

import java.lang.annotation.*;

/**
 * @ClassName: TestEndPoint
 * @Description
 * @Author Mr.L
 * @Date 2020/12/10 11:03
 * @Version 1.0
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Service
public @interface TestEndPoint {
    @AliasFor(annotation = Service.class)
    String value() default "";

    String desc();
}

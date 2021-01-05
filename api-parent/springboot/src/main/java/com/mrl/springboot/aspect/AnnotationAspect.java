package com.mrl.springboot.aspect;

import com.mrl.springboot.annotation.PreAuthorize;
import com.mrl.springboot.exception.UnAuthorizedException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

/**
 * @ClassName: AnnotationAspect
 * @Description
 * @Author Mr.L
 * @Date 2020/12/10 13:44
 * @Version 1.0
 */
@Slf4j
@Aspect
@Component
public class AnnotationAspect extends AspectSupport {

    @Pointcut("@annotation(com.mrl.springboot.annotation.PreAuthorize)")
    public void pointCut() {
    }

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Method method = resolveMethod(joinPoint);
        PreAuthorize testException = method.getAnnotation(PreAuthorize.class);
        String op = testException.operation();
        // test data
        List<String> auth = Arrays.asList("admin:delete", "admin:create");
        if (!auth.contains(op)) {
            throw new UnAuthorizedException(testException.exception());
        } else {
            return joinPoint.proceed();
        }
    }
}

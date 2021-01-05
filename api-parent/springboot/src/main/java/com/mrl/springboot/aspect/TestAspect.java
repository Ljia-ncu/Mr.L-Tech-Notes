package com.mrl.springboot.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * @ClassName: TestAdvice
 * @Description
 * @Author Mr.L
 * @Date 2020/12/10 10:31
 * @Version 1.0
 */

@Slf4j
@Aspect
@Component
public class TestAspect {

    @Pointcut("execution(* com.mrl.springboot.service.TestService.*(..))")
    public void pointCut() {
    }

    @Before("pointCut()")
    public void beforeMethod(JoinPoint joinPoint) {
        log.info("before Method");
    }

    @After("pointCut()")
    public void afterMethod(JoinPoint joinPoint) {
        log.info("after Method");
    }

    @AfterReturning(value = "pointCut()", returning = "result")
    public void afterReturningMethod(JoinPoint joinPoint, Object result) {
        log.info("after returning ");
        if (result != null) {
            log.info(String.valueOf(result));
        }
    }

    @AfterThrowing(value = "pointCut()", throwing = "exception")
    public void afterThrowing(JoinPoint joinPoint, Throwable exception) {
        log.info("after throwing " + exception.getMessage());
    }

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        log.info("pre");
        Object obj = proceedingJoinPoint.proceed();
        log.info("post");
        return obj;
    }
}

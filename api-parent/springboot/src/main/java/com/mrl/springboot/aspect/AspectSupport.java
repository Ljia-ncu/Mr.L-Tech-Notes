package com.mrl.springboot.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

/**
 * @ClassName: AspectSupport
 * @Description
 * @Author Mr.L
 * @Date 2020/12/10 11:14
 * @Version 1.0
 */
public abstract class AspectSupport {

    public Method resolveMethod(ProceedingJoinPoint proceedingJoinPoint) {
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        Class<?> clazz = proceedingJoinPoint.getTarget().getClass();
        Method method = getDeclaredMethod(clazz, methodSignature.getName(), methodSignature.getParameterTypes());
        if (method == null) {
            throw new IllegalStateException("can't resolve method:" + methodSignature.getMethod().getName());
        }
        return method;
    }

    private Method getDeclaredMethod(Class<?> clazz, String methodName, Class<?>... paramType) {
        try {
            return clazz.getDeclaredMethod(methodName, paramType);
        } catch (NoSuchMethodException e) {
            Class<?> superClass = clazz.getSuperclass();
            if (superClass != null) {
                return getDeclaredMethod(superClass, methodName, paramType);
            }
        }
        return null;
    }
}

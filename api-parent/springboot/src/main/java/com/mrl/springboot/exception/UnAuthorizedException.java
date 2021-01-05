package com.mrl.springboot.exception;

/**
 * @ClassName: UnAuthorizedException
 * @Description 继承RuntimeException
 * @Author Mr.L
 * @Date 2020/12/10 14:41
 * @Version 1.0
 */
public class UnAuthorizedException extends RuntimeException {

    public UnAuthorizedException(String message) {
        super(message);
    }
}

package com.mrl.springboot.handler;

import com.mrl.springboot.exception.UnAuthorizedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @ClassName: GlobalExceptionHandler
 * @Description 全局异常处理，最高优先级
 * @Author Mr.L
 * @Date 2020/12/10 15:20
 * @Version 1.0
 */
@Slf4j
@Order(value = Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<?> handleException(Exception e) {
        log.error("内部异常", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }

    /**
     * UnAuthorizedException 继承于RuntimeException;
     */
    @ExceptionHandler(value = UnAuthorizedException.class)
    public ResponseEntity<?> handleUnAuthorizedException(UnAuthorizedException e) {
        log.error("illegal access,unAuthorized " + e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }
}

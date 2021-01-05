package com.mrl.sentinel.handler;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.http.ResponseEntity;

/**
 * @ClassName: CustomBlockHandler
 * @Description 注意 "handleException" 应声明为静态方法，参数可以传入BlockException、requestMapping_params..
 * @Author Mr.L
 * @Date 2020/12/17 23:51
 * @Version 1.0
 */
public class CustomBlockHandler {
    public static ResponseEntity<String> handleException(BlockException exception) {
        return ResponseEntity.ok(exception.getClass().getCanonicalName());
    }
}

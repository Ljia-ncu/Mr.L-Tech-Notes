package com.mrl.sentinel.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.mrl.sentinel.feign.NacosClientService;
import com.mrl.sentinel.handler.CustomBlockHandler;
import com.sun.deploy.security.BlockedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ClassName: RateLimitController
 * @Description 通过@SentinelResource来定义限流行为
 * @Author Mr.L
 * @Date 2020/12/17 16:27
 * @Version 1.0
 */
@Controller
@RequestMapping("/sentinel")
public class RateLimitController {

    @Autowired
    private NacosClientService service;

    @GetMapping("/getNacosClientConfig/{id}")
    @SentinelResource(value = "nacos-client-fallback", blockHandler = "handleException")
    public ResponseEntity<String> getNacosClientConfig(@PathVariable Integer id) {
        return service.getConfig(id);
    }

    public ResponseEntity<String> handleException(Integer id, BlockedException exception) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(id + exception.getMessage());
    }

    /**
     * 按资源名称限流
     * -> com.alibaba.csp.sentinel.slots.block.flow.FlowException
     */
    @GetMapping("/resource")
    @SentinelResource(value = "byResource", blockHandler = "handleException", blockHandlerClass = {CustomBlockHandler.class})
    public ResponseEntity<String> get() {
        return ResponseEntity.ok("limit_resource");
    }

    /**
     * 按url限流
     * -> Blocked by Sentinel (flow limiting)
     */
    @GetMapping("/url")
    @SentinelResource(value = "byUrl")
    public ResponseEntity<String> get2() {
        return ResponseEntity.ok("limit_url");
    }
}

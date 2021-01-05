package com.mrl.redis.controller;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @ClassName: LockController
 * @Description
 * @Author Mr.L
 * @Date 2020/12/27 20:34
 * @Version 1.0
 */
@Controller
public class LockController {

    /**
     * jmeter测试非线程安全操作，模拟写db数剧
     */
    private int count = 0;

    @Autowired
    private RedissonClient redissonClient;

    /**
     * 对照测试
     * 2s 500请求 +497
     */
    @GetMapping("/test")
    public ResponseEntity<Integer> lock2() {
        count++;
        return ResponseEntity.ok(count);
    }

    /**
     * 2s 500请求 +500
     */
    @GetMapping("/rlock")
    public ResponseEntity<Integer> lock() {
        RLock lock = redissonClient.getLock("mrl");
        lock.lock();
        try {
            count++;
        } finally {
            lock.unlock();
        }
        return ResponseEntity.ok(count);
    }
}

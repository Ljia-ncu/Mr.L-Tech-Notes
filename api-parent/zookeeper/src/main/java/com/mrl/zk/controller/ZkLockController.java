package com.mrl.zk.controller;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: ZkLockController
 * @Description
 * @Author Mr.L
 * @Date 2020/12/28 16:10
 * @Version 1.0
 */
@RestController
public class ZkLockController {

    @Autowired
    private CuratorFramework client;

    /**
     * jmeter 测试数据，非线程安全
     */
    private Integer count = 0;

    @GetMapping("/lock")
    public Integer getCount() throws Exception {
        InterProcessMutex mutex = new InterProcessMutex(client, "/mrl");
        try {
            mutex.acquire();
            count++;
        } catch (Exception e) {
            if (e instanceof RuntimeException) {
                throw e;
            }
        } finally {
            mutex.release();
        }
        return count;
    }
}

package com.mrl.zk;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @ClassName: ZkLockTest
 * @Description
 * @Author Mr.L
 * @Date 2020/12/28 13:23
 * @Version 1.0
 */
@SpringBootTest
public class ZkLockTest {

    @Autowired
    private CuratorFramework curatorFramework;

    private int count = 0;

    @Test
    public void lock() throws Exception {
        InterProcessMutex mutex = new InterProcessMutex(curatorFramework, "/mrl");
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
    }
}

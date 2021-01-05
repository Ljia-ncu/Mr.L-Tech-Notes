package com.mrl.zk.config;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName: CuratorConfig
 * @Description
 * @Author Mr.L
 * @Date 2020/12/28 13:11
 * @Version 1.0
 */
@Configuration
public class CuratorConfig {

    @Value("${zk.cluster}")
    private String zkCluster;

    @Bean
    public CuratorFramework curatorFramework() {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.newClient(zkCluster, retryPolicy);
        client.start();
        return client;
    }
}

package com.mrl.nacos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @ClassName: NacosApplication
 * @Description
 * @Author Mr.L
 * @Date 2020/12/15 23:50
 * @Version 1.0
 */
@EnableDiscoveryClient
@SpringBootApplication
public class NacosApplication {
    public static void main(String[] args) {
        SpringApplication.run(NacosApplication.class, args);
    }
}

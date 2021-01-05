package com.mrl.openfeign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @ClassName: OpenFeignApplication
 * @Description
 * @Author Mr.L
 * @Date 2020/12/14 20:47
 * @Version 1.0
 */
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class OpenFeignApplication {
    public static void main(String[] args) {
        SpringApplication.run(OpenFeignApplication.class, args);
    }
}

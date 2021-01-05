package com.mrl.admin;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @ClassName: SpringAdminApplication
 * @Description
 * @Author Mr.L
 * @Date 2020/12/14 20:57
 * @Version 1.0
 */
@EnableAdminServer
@EnableDiscoveryClient
@SpringBootApplication
public class SpringAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringAdminApplication.class, args);
    }
}

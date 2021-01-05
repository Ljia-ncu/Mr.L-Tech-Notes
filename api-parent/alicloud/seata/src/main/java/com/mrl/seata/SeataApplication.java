package com.mrl.seata;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @ClassName: SeataApplication
 * @Description
 * @Author Mr.L
 * @Date 2020/12/18 23:58
 * @Version 1.0
 */
@MapperScan("com.mrl.seata.mapper")
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class SeataApplication {
    public static void main(String[] args) {
        SpringApplication.run(SeataApplication.class, args);
    }
}

package com.mrl.nginx.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: LocationController
 * @Description
 * @Author Mr.L
 * @Date 2021/1/3 20:53
 * @Version 1.0
 */
@RestController
public class LocationController {
    @Value("${server.port}")
    private int port;

    @GetMapping("/test")
    public String location() {
        return (port == 8081 ? "location1:" : "location2:") + port;
    }
}

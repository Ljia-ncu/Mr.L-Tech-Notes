package com.mrl.nacos.controller;

import com.mrl.nacos.properties.RefreshProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: TestController
 * @Description
 * @Author Mr.L
 * @Date 2020/12/16 14:04
 * @Version 1.0
 */
@RestController
public class TestController {

    @Autowired
    private RefreshProperties properties;

    @GetMapping("/config/{id}")
    public ResponseEntity<String> getConfig(@PathVariable Integer id) {
        return ResponseEntity.ok(id + properties.getInfo());
    }
}

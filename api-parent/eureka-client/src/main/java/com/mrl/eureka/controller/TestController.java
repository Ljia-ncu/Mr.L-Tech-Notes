package com.mrl.eureka.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName: TestController
 * @Description
 * @Author Mr.L
 * @Date 2020/12/14 18:11
 * @Version 1.0
 */
@RestController
public class TestController {

    @Value("${server.port}")
    private Integer port;

    @RequestMapping(value = "/client", method = RequestMethod.GET)
    ResponseEntity<String> getClient(@RequestParam String tag) {
        return ResponseEntity.ok().body(tag + ":lb:client " + port);
    }

    @RequestMapping(value = "/client/deleteClient/{id}", method = RequestMethod.POST)
    ResponseEntity<String> postClient(@PathVariable Integer id) {
        return ResponseEntity.ok().body(id + "-client deleted");
    }
}

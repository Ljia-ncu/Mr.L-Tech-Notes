package com.mrl.dubbo.controller;

import com.mrl.dubbo.service.TestService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @ClassName: EchoController
 * @Description
 * @Author Mr.L
 * @Date 2020/12/24 23:48
 * @Version 1.0
 */
@Controller
public class EchoController {

    @DubboReference(version = "1.0.0", group = "mrl")
    private TestService testService;

    @RequestMapping("/echo")
    public ResponseEntity<String> echo(@RequestParam String word) {
        return ResponseEntity.ok(testService.echo(word));
    }
}

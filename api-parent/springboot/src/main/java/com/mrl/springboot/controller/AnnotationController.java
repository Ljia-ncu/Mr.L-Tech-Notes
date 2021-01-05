package com.mrl.springboot.controller;

import com.mrl.springboot.service.AnnotationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: AnnotationController
 * @Description
 * @Author Mr.L
 * @Date 2020/12/10 15:36
 * @Version 1.0
 */
@RestController
public class AnnotationController {

    @Autowired
    private AnnotationService service;

    @RequestMapping(value = "/err", method = RequestMethod.GET)
    public ResponseEntity<?> get() {
        int i = 1 / 0;
        return ResponseEntity.ok("ok");
    }

    @RequestMapping(value = "/authErr/{n}", method = RequestMethod.GET)
    public ResponseEntity<?> get2(@PathVariable int n) {
        Integer res = service.resolve2(n);
        return ResponseEntity.ok(res);
    }

    @RequestMapping(value = "/auth/{n}", method = RequestMethod.GET)
    public ResponseEntity<?> get3(@PathVariable int n) {
        Integer res = service.resolve(n);
        return ResponseEntity.ok(res);
    }
}

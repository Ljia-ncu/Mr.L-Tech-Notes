package com.mrl.seata.controller;

import com.mrl.seata.service.TxTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: TxController
 * @Description
 * @Author Mr.L
 * @Date 2020/12/19 13:02
 * @Version 1.0
 */
@RestController
public class TxController {

    @Autowired
    private TxTestService service;

    /**
     * 抛出ArithmeticException 除0异常前，依然会成功更新db
     * Transactional 只保证本地事务
     */
    @GetMapping("/feign/test")
    public ResponseEntity<String> test() {
        service.test();
        return ResponseEntity.ok().body("200");
    }

    /**
     * 转账例子
     * GlobalTransaction 保证全局事务
     */
    @GetMapping("/transfer")
    public ResponseEntity<String> transfer(@RequestParam("userId") String userId, @RequestParam("money") Integer money) {
        service.pay(userId, money);
        return ResponseEntity.ok().body("200");
    }

    @GetMapping("/reduce")
    Boolean reduce(@RequestParam("userId") String userId, @RequestParam("money") Integer money) {
        service.reduce(userId, money);
        return true;
    }

    @GetMapping("/plus")
    Boolean plus(@RequestParam("userId") String userId, @RequestParam("money") Integer count) {
        service.plus(userId, count);
        return true;
    }
}

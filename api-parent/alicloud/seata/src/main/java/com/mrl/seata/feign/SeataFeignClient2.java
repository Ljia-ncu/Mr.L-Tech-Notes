package com.mrl.seata.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @InterfaceName: SeataFeignClient2
 * @Description 模拟扣减库存
 * @Author Mr.L
 * @Date 2020/12/19 12:00
 * @Version 1.0
 */
@FeignClient(name = "seata2")
public interface SeataFeignClient2 {
    @GetMapping("/plus")
    Boolean plus(@RequestParam("userId") String userId, @RequestParam("money") Integer money);
}
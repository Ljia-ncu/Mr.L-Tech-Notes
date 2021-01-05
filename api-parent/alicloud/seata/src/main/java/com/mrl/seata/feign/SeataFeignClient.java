package com.mrl.seata.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @InterfaceName: Seata
 * @Description 模拟扣减金币
 * @Author Mr.L
 * @Date 2020/12/19 11:58
 * @Version 1.0
 */
@FeignClient(name = "seata1")
public interface SeataFeignClient {
    @GetMapping("/reduce")
    Boolean reduce(@RequestParam("userId") String userId, @RequestParam("money") Integer money);
}

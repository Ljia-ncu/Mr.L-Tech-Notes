package com.mrl.openfeign.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @ClassName: ClientInterface
 * @Description 用于暴露eureka-client的对外服务
 * 注意不能使用ResponseEntity<?> 通配符，每个参数名称也必须通过@PathVariable/@RequestBody/@RequestParam等value明确
 * @Author Mr.L
 * @Date 2020/12/14 21:55
 * @Version 1.0
 */
public interface ClientInterface {
    @RequestMapping(value = "/client/deleteClient/{id}", method = RequestMethod.POST)
    ResponseEntity<String> postClient(@PathVariable Integer id);

    @RequestMapping(value = "/client", method = RequestMethod.GET)
    ResponseEntity<String> getClient(@RequestParam String tag);
}

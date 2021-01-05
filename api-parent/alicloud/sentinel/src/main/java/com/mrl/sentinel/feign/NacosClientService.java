package com.mrl.sentinel.feign;

import com.mrl.sentinel.fallback.NacosClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @InterfaceName: NacosClientFeign
 * @Description,
 * @Author Mr.L
 * @Date 2020/12/17 16:44
 * @Version 1.0
 */
@FeignClient(name = "nacos-client", fallback = NacosClientFallback.class)
public interface NacosClientService {

    @GetMapping("/config/{id}")
    ResponseEntity<String> getConfig(@PathVariable Integer id);
}

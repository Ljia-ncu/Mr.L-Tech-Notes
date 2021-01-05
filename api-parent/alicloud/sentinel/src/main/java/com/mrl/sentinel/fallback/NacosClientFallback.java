package com.mrl.sentinel.fallback;

import com.mrl.sentinel.feign.NacosClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 * @ClassName: NacosClientFallback
 * @Description
 * @Author Mr.L
 * @Date 2020/12/17 16:48
 * @Version 1.0
 */
@Component
public class NacosClientFallback implements NacosClientService {

    @Override
    public ResponseEntity<String> getConfig(Integer id) {
        return ResponseEntity.ok("fallback request " + id);
    }
}

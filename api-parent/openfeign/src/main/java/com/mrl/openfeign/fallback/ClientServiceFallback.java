package com.mrl.openfeign.fallback;

import com.mrl.openfeign.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 * @ClassName: ClientServiceFallback
 * @Description
 * @Author Mr.L
 * @Date 2020/12/15 16:41
 * @Version 1.0
 */
@Component
public class ClientServiceFallback implements ClientService {

    @Override
    public ResponseEntity<String> postClient(Integer id) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("error");
    }

    @Override
    public ResponseEntity<String> getClient(String tag) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("error");
    }
}

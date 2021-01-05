package com.mrl.openfeign.service;

import com.mrl.openfeign.api.ClientInterface;
import com.mrl.openfeign.fallback.ClientServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @InterfaceName: ClientService
 * @Description
 * @Author Mr.L
 * @Date 2020/12/14 22:21
 * @Version 1.0
 */
@FeignClient(value = "eureka-client", fallback = ClientServiceFallback.class)
public interface ClientService extends ClientInterface {

}

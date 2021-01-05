package com.mrl.admin.config;

import org.springframework.boot.actuate.trace.http.InMemoryHttpTraceRepository;

/**
 * @ClassName: HttpTraceConfig
 * @Description 推荐zipkin、Prometheus+Grafana
 * @Author Mr.L
 * @Date 2020/12/15 15:05
 * @Version 1.0
 */
//@Configuration
public class HttpTraceConfig {
    //@Bean
    public InMemoryHttpTraceRepository inMemoryHttpTraceRepository() {
        return new InMemoryHttpTraceRepository();
    }
}

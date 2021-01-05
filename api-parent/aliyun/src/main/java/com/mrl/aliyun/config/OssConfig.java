package com.mrl.aliyun.config;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.mrl.aliyun.properties.OssProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName: OssConfig
 * @Description
 * @Author Mr.L
 * @Date 2020/12/9 22:46
 * @Version 1.0
 */
@Configuration
public class OssConfig {

    @Autowired
    private OssProperties properties;

    @Bean
    public OSS oss() {
        return new OSSClientBuilder().build(properties.getEndpoint(), properties.getAccessKeyId(), properties.getAccessKeySecret());
    }
}

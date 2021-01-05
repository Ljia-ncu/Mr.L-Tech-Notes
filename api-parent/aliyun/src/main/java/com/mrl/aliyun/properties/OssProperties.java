package com.mrl.aliyun.properties;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @ClassName: OssProperties
 * @Description
 * @Author Mr.L
 * @Date 2020/12/12 0:27
 * @Version 1.0
 */
@Getter
@Component
@ConfigurationProperties(prefix = "aliyun.oss")
public class OssProperties {
    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
    private Long policyExpire;
    private Long maxSize;
    private String callback;
    private String bucketName;
    private String dirPrefix;
}

package com.mrl.nacos.properties;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * @ClassName: RefreshProperties
 * @Description
 * @Author Mr.L
 * @Date 2020/12/16 14:02
 * @Version 1.0
 */
@Getter
@RefreshScope
@Component
public class RefreshProperties {

    @Value("${config.info}")
    private String info;
}

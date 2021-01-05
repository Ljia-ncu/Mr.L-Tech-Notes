package com.mrl.dubbo.service;

import org.apache.dubbo.config.annotation.DubboService;

/**
 * @ClassName: EchoService
 * @Description
 * @Author Mr.L
 * @Date 2020/12/24 21:27
 * @Version 1.0
 */
@DubboService(version = "1.0.0", group = "mrl")
public class EchoServiceImpl implements TestService {
    @Override
    public String echo(String word) {
        return "server echo:" + word;
    }
}

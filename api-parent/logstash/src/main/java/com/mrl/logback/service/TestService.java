package com.mrl.logback.service;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: TestController
 * @Description
 * @Author Mr.L
 * @Date 2020/12/11 10:42
 * @Version 1.0
 */
@Slf4j
@Service
public class TestService {

    public void test() {
        Map<String, String> map = new HashMap<>(8);
        map.put("IP", "127.0.0.1");
        map.put("Operation", "test()");
        map.put("Result", "200");
        log.info(JSONUtil.parse(map).toString());
    }
}

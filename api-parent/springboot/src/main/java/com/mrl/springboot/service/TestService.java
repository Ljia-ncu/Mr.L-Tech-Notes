package com.mrl.springboot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @ClassName: TestService
 * @Description
 * @Author Mr.L
 * @Date 2020/12/10 10:47
 * @Version 1.0
 */
@Slf4j
@Service
public class TestService extends SuperClass {

    @Override
    public Integer doSth() {
        log.info("Service doSth....");
        return 1;
    }

    public void doSth2() throws Exception {
        throw new Exception("err");
    }
}

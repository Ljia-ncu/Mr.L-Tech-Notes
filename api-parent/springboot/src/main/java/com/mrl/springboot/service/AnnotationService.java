package com.mrl.springboot.service;

import com.mrl.springboot.annotation.PreAuthorize;
import com.mrl.springboot.annotation.TestEndPoint;

/**
 * @ClassName: TestEndPoint
 * @Description
 * @Author Mr.L
 * @Date 2020/12/10 13:41
 * @Version 1.0
 */
@TestEndPoint(desc = "...")
public class AnnotationService {

    @PreAuthorize(operation = "admin:delete", exception = "UnAuthorized,403")
    public Integer resolve(Integer n) {
        return n;
    }

    @PreAuthorize(operation = "admin:test", exception = "UnAuthorized,403")
    public Integer resolve2(Integer n) {
        return n;
    }
}

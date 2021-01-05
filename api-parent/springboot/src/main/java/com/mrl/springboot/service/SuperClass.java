package com.mrl.springboot.service;

/**
 * @ClassName: SuperClass
 * @Description
 * @Author Mr.L
 * @Date 2020/12/10 14:00
 * @Version 1.0
 */
public class SuperClass {

    public Integer doSth() {
        System.out.println("super doSth...");
        return 0;
    }

    public Integer test() {
        System.out.println("aop super class");
        return -1;
    }
}

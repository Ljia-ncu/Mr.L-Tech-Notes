package com.mrl.redis.util;

/**
 * @ClassName: RedisEnum
 * @Description
 * @Author Mr.L
 * @Date 2020/11/30 10:46
 * @Version 1.0
 */
public enum ConstantUtil {
    //hash_prefix
    GOODS_PREFIX("test:goods"),
    ORDER_PREFIX("test:order"),
    SOCRE_PREFIX("score:range");

    private final String key;

    ConstantUtil(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}

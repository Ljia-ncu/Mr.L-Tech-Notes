package com.mrl.redis.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @ClassName: Sub
 * @Description
 * @Author Mr.L
 * @Date 2020/11/30 11:47
 * @Version 1.0
 */
@Data
@Accessors(chain = true)
public class Goods implements Serializable {
    private Integer id;
    private BigDecimal price;
    private Integer stock;
    private String url;
}

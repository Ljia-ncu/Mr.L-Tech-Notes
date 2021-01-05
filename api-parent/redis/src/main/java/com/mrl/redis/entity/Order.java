package com.mrl.redis.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @ClassName: Info
 * @Description
 * @Author Mr.L
 * @Date 2020/11/30 11:18
 * @Version 1.0
 */
@Data
@Accessors(chain = true)
public class Order implements Serializable {
    private String orderNo;
    private List<Goods> goods;
    private String remark;
    private Date date;
}

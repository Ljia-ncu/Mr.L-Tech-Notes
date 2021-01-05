package com.mrl.mybatisplus.vo;

import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @ClassName: InfoVo
 * @Description
 * @Author Mr.L
 * @Date 2020/11/29 1:09
 * @Version 1.0
 */
@Setter
public class InfoVo implements Serializable {
    private Integer id;

    private String name;

    private Integer age;

    private BigDecimal account;
}

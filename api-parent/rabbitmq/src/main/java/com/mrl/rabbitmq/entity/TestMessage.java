package com.mrl.rabbitmq.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName: Message
 * @Description
 * @Author Mr.L
 * @Date 2020/12/9 14:27
 * @Version 1.0
 */
@Data
public class TestMessage implements Serializable {
    private Integer id;
    private Integer status;
    private String message;
}

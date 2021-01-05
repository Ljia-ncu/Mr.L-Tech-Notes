package com.mrl.security.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName: User
 * @Description
 * @Author Mr.L
 * @Date 2020/12/3 12:16
 * @Version 1.0
 */
@Data
public class Admin implements Serializable {
    private Integer id;
    private String username;
    private String password;
    //...
}

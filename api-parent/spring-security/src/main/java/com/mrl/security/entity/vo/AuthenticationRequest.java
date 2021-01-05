package com.mrl.security.entity.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName: AuthenticationRequest
 * @Description
 * @Author Mr.L
 * @Date 2020/12/3 12:15
 * @Version 1.0
 */
@Setter
@Getter
public class AuthenticationRequest {
    private String username;
    private String password;
    private String code;
}

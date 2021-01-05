package com.mrl.security.entity.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName: RegisterVo
 * @Description
 * @Author Mr.L
 * @Date 2020/12/3 13:59
 * @Version 1.0
 */
@Setter
@Getter
public class RegisterForm {
    private String username;
    private String password;
    private String phone;
    private String email;
    //...
}

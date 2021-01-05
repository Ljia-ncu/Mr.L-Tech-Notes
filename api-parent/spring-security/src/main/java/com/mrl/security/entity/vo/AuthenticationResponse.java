package com.mrl.security.entity.vo;

/**
 * @ClassName: AuthenticationResponse
 * @Description
 * @Author Mr.L
 * @Date 2020/12/3 12:16
 * @Version 1.0
 */
public class AuthenticationResponse {
    private final String jwt;

    public AuthenticationResponse(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return this.jwt;
    }
}

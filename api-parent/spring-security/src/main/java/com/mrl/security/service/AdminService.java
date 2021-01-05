package com.mrl.security.service;

import com.mrl.security.entity.Admin;

/**
 * @ClassName: UserService
 * @Description
 * @Author Mr.L
 * @Date 2020/12/1 16:55
 * @Version 1.0
 */
public interface AdminService {

    String authenticateAndGetJwt(String username, String password) throws Exception;

    void register(Admin user);

    Admin getAdminByName(String name);
}

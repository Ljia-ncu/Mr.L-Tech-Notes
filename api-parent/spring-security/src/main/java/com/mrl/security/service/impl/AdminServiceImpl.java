package com.mrl.security.service.impl;

import com.mrl.security.entity.Admin;
import com.mrl.security.mapper.AdminMapper;
import com.mrl.security.service.AdminService;
import com.mrl.security.util.JwtUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @ClassName: UserServiceImpl
 * @Description
 * @Author Mr.L
 * @Date 2020/12/1 16:56
 * @Version 1.0
 */
@Log4j2
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public String authenticateAndGetJwt(String username, String password) throws Exception {
        Authentication authentication = null;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }
        return jwtUtil.generateToken((UserDetails) authentication.getPrincipal());
    }

    @Override
    public void register(Admin user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        adminMapper.put(user.getUsername(), user);
    }

    @Override
    public Admin getAdminByName(String name) {
        return adminMapper.getAdminByName(name);
    }
}

package com.mrl.security.component;

import com.mrl.security.entity.Admin;
import com.mrl.security.mapper.AdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName: JwtUserDetailService
 * @Description
 * @Author Mr.L
 * @Date 2020/12/3 13:09
 * @Version 1.0
 */
@Component
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = adminMapper.getAdminByName(username);
        if (admin != null) {
            List<String> auth = adminMapper.getAdminAuth(username);
            return new User(admin.getUsername(), admin.getPassword(),
                    auth.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
        }
        throw new UsernameNotFoundException("Incorrect username or password");
    }
}

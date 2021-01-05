package com.mrl.admin.config;

import de.codecentric.boot.admin.server.config.AdminServerProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

/**
 * @ClassName: SecurityConfig
 * @Description
 * @Author Mr.L
 * @Date 2020/12/15 11:45
 * @Version 1.0
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AdminServerProperties properties;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        String path = properties.getContextPath();
        http.authorizeRequests()
                .antMatchers(path + "/assets/**").permitAll()
                .antMatchers(path + "/login").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage(path + "/login").successHandler(successHandler())
                .and()
                .logout().logoutUrl(path + "/logout")
                .and()
                .httpBasic()
                .and()
                .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .ignoringAntMatchers(path + "/instances", path + "/actuator/**");
    }

    private AuthenticationSuccessHandler successHandler() {
        SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
        successHandler.setTargetUrlParameter("redirectTo");
        successHandler.setDefaultTargetUrl(properties.getContextPath() + "/");
        return successHandler;
    }
}

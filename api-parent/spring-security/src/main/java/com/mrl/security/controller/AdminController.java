package com.mrl.security.controller;

import com.mrl.security.entity.Admin;
import com.mrl.security.entity.vo.AuthenticationRequest;
import com.mrl.security.entity.vo.AuthenticationResponse;
import com.mrl.security.entity.vo.RegisterForm;
import com.mrl.security.service.AdminService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName: UserController
 * @Description
 * @Author Mr.L
 * @Date 2020/12/1 16:59
 * @Version 1.0
 */
@RestController
public class AdminController {

    @Autowired
    private AdminService adminService;

    @RequestMapping(path = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> authenticateAndGetJwt(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        String jwt = adminService.authenticateAndGetJwt(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> register(@RequestBody RegisterForm form) {
        Admin admin = new Admin();
        BeanUtils.copyProperties(form, admin);
        adminService.register(admin);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAuthority('admin')")
    @RequestMapping(path = "/find/{name}", method = RequestMethod.GET)
    public Admin getAdmin(@PathVariable("name") String name) {
        return adminService.getAdminByName(name);
    }

    @PreAuthorize("hasAuthority('mrl')")
    @RequestMapping(path = "/op", method = RequestMethod.GET)
    public Object getAdmin2(String name) {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}

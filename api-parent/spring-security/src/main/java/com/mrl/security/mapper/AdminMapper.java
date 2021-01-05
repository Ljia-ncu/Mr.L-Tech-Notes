package com.mrl.security.mapper;

import com.mrl.security.entity.Admin;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName: AdminMapper
 * @Description test data
 * @Author Mr.L
 * @Date 2020/12/3 18:31
 * @Version 1.0
 */
@Service
public class AdminMapper {
    private final Map<String, Admin> db = new ConcurrentHashMap<>();

    public Admin getAdminByName(String name) {
        return db.get(name);
    }

    public List<String> getAdminAuth(String name) {
        return Arrays.asList("admin", "tester");
    }

    public void put(String name, Admin admin) {
        db.put(name, admin);
    }
}

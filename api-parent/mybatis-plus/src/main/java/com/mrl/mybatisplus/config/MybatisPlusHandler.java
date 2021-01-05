package com.mrl.mybatisplus.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @ClassName: MybatisPlusHandler
 * @Description
 * @Author Mr.L
 * @Date 2020/11/28 23:10
 * @Version 1.0
 */
@Component
public class MybatisPlusHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("createDate", new Date(), metaObject);
        this.setFieldValByName("updateDate", new Date(), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateDate", new Date(), metaObject);
    }
}

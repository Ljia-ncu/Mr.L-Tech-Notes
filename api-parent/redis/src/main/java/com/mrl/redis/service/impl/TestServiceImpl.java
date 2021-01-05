package com.mrl.redis.service.impl;

import com.mrl.redis.entity.Goods;
import com.mrl.redis.service.TestService;
import org.springframework.stereotype.Service;

/**
 * @ClassName: TestServiceImpl
 * @Description
 * @Author Mr.L
 * @Date 2020/11/30 17:01
 * @Version 1.0
 */
@Service
public class TestServiceImpl implements TestService {

    @Override
    public Goods get(Integer id) {
        Goods goods = new Goods().setId(id).setUrl("tmp");
        System.out.println("got" + goods);
        return goods;
    }

    @Override
    public Goods add(Goods goods) {
        System.out.println("added" + goods);
        return goods;
    }

    @Override
    public int deleteOrUpdate(Goods goods) {
        System.out.println("deleted" + goods);
        return 1;
    }

    @Override
    public Goods put(Goods goods) {
        System.out.println("put" + goods);
        return goods;
    }
}

package com.mrl.seata.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mrl.seata.entity.TxTest;
import com.mrl.seata.feign.SeataFeignClient;
import com.mrl.seata.feign.SeataFeignClient2;
import com.mrl.seata.mapper.TxTestMapper;
import com.mrl.seata.service.TxTestService;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Mr.L
 * @since 2020-12-19
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TxTestServiceImpl extends ServiceImpl<TxTestMapper, TxTest> implements TxTestService {

    @Autowired
    private SeataFeignClient client1;

    @Autowired
    private SeataFeignClient2 client2;

    /**
     * 转账
     */
    @Override
    @GlobalTransactional(timeoutMills = 300000, name = "seata-service-tx")
    public void pay(String userId, Integer coin) {
        client1.reduce(userId, coin);
        //int i = 1/0;
        client2.plus(userId, coin);
    }

    @Override
    public void reduce(String userId, Integer coin) {
        baseMapper.reduce(userId, coin);
    }

    @Override
    public void plus(String userId, Integer coin) {
        baseMapper.plus(userId, coin);
    }

    @Override
    public void test() {
        client1.reduce("965", 1);
        int i = 1 / 0;
        client2.plus("965", 1);
    }
}

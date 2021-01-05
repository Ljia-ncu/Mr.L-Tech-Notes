package com.mrl.seata.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mrl.seata.entity.TxTest;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Mr.L
 * @since 2020-12-19
 */
public interface TxTestService extends IService<TxTest> {

    void pay(String userId, Integer coin);

    void reduce(String userId, Integer coin);

    void plus(String userId, Integer coin);

    void test();
}

package com.mrl.seata.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mrl.seata.entity.TxTest;
import org.apache.ibatis.annotations.Update;

/**
 * @InterfaceName: TxTestMapper
 * @Description
 * @Author Mr.L
 * @Date 2020/12/19 11:50
 * @Version 1.0
 */
public interface TxTestMapper extends BaseMapper<TxTest> {

    @Update("update tx_test set count = count - #{count} where userId = #{userId}")
    void reduce(String userId, Integer count);

    @Update("update tx_test set count = count + #{count} where userId = #{userId}")
    void plus(String userId, Integer count);
}

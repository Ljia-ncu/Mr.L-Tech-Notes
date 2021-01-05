package com.mrl.redis.service;

import com.mrl.redis.entity.Goods;
import org.springframework.cache.annotation.*;

/**
 * @ClassName: TestService
 * @Description
 * @Author Mr.L
 * @Date 2020/11/30 17:00
 * @Version 1.0
 */
@CacheConfig(cacheNames = "method:cache")
public interface TestService {
    /**
     * 缓存命中则反序列化缓存结果，否则将结果放入缓存
     *
     * @param id
     * @return
     */
    @Cacheable(key = "#id", condition = "#id >1 and #id <15")
    Goods get(Integer id);

    /**
     * 将结果放入缓存，用于新增操作
     *
     * @param goods
     * @return
     */
    @CachePut(key = "#goods.id")
    Goods add(Goods goods);

    /**
     * 根据id删除缓存，在AOP后置处理阶段中完成删除操作
     * 用于更新或删除操作，对于更新操作 需要通过再次查询将结果放入缓存
     *
     * @param goods
     * @return
     */
    @CacheEvict(key = "#goods.id")
    int deleteOrUpdate(Goods goods);

    /**
     * 可以同时生效多个注解，see @Caching
     *
     * @param goods
     * @return
     */
    @Caching(put = {@CachePut(key = "#goods.id"), @CachePut(key = "#goods.url")})
    Goods put(Goods goods);
}

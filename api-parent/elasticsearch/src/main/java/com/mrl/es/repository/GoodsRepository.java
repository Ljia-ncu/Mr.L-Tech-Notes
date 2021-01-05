package com.mrl.es.repository;

import com.mrl.es.pojo.Goods;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @InterfaceName: GoodsRepository
 * @Description
 * @Author Mr.L
 * @Date 2020/12/8 14:25
 * @Version 1.0
 */
public interface GoodsRepository extends ElasticsearchRepository<Goods, Long> {
}

package com.mrl.es.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: SearchResponse
 * @Description 整合聚合结果与查询结果
 * @Author Mr.L
 * @Date 2020/12/7 17:14
 * @Version 1.0
 */
@Data
public class SearchResponseVO implements Serializable {

    private AggregationAttr brand;
    private AggregationAttr category;
    private List<AggregationAttr> attrs = new ArrayList<>();

    private List<Goods> goods = new ArrayList<>();

    private Long total;
    private Integer pageSize;
    private Integer pageNum;
}

package com.mrl.es.pojo;

import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName: SearchParam
 * @Description
 * @Author Mr.L
 * @Date 2020/12/6 17:58
 * @Version 1.0
 */
@Setter
@Getter
public class SearchParam {

    /**
     * 多分类检索
     */
    private String[] categoryId;

    /**
     * 多品牌检索
     */
    private String[] brandId;

    /**
     * 关键字
     */
    private String keyword;

    /**
     * 基于nested属性检索，e.g. attrId:attrValue1-attrValue2
     */
    private String[] attr;

    /**
     * 排序,e.g. price:asc / sales:desc /...
     */
    private String order;

    private Integer pageNum = 1;
    private Integer pageSize = 20;

    private Integer priceFrom;
    private Integer priceTo;
}

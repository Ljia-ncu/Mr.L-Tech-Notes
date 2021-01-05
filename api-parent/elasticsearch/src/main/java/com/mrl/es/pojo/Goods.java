package com.mrl.es.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Description
 * @ClassName: Goods
 * @Author Mr.L
 * @Date 2020/12/7 11:08
 * @Version 1.0
 */
@Data
@Document(indexName = "goods", replicas = 0)
public class Goods implements Serializable {
    @Id
    private Long skuId;

    @Field(type = FieldType.Text, index = false)
    private String pic;

    @Field(analyzer = "ik_max_word", type = FieldType.Text)
    private String title;
    @Field(analyzer = "ik_max_word", type = FieldType.Text)
    private String subtitle;

    @Field(type = FieldType.Double)
    private BigDecimal oriPrice;
    @Field(type = FieldType.Double)
    private BigDecimal curPrice;

    @Field(type = FieldType.Long)
    private Long sales;
    @Field(type = FieldType.Long)
    private Long stock;

    @Field(type = FieldType.Integer)
    private Integer status;
    @Field(type = FieldType.Date, format = DateFormat.basic_date)
    private Date onSaleDate;

    @Field(type = FieldType.Long)
    private Long brandId;
    @Field(type = FieldType.Keyword)
    private String brandName;

    @Field(type = FieldType.Long)
    private Long categoryId;
    @Field(type = FieldType.Keyword)
    private String categoryName;

    @Field(type = FieldType.Nested)
    private List<GoodsSearchAttr> attrs;
}

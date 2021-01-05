package com.mrl.es.pojo;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

/**
 * @ClassName: SearchAttr
 * @Description
 * @Author Mr.L
 * @Date 2020/12/7 12:50
 * @Version 1.0
 */
@Data
public class GoodsSearchAttr implements Serializable {

    @Field(type = FieldType.Long)
    private Long attrId;

    @Field(type = FieldType.Keyword)
    private String attrName;

    /**
     * 大小写敏感，写入或查询时需统一
     */
    @Field(type = FieldType.Keyword)
    private String attrValue;
}

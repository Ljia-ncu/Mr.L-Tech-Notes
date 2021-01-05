package com.mrl.es.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @ClassName: Book
 * @Description
 * @Author Mr.L
 * @Date 2020/12/5 13:44
 * @Version 1.0
 */
@Data
@Accessors(chain = true)
@Document(indexName = "product", replicas = 0)
public class Book implements Serializable {
    @Id
    private Long bookId;

    @Field(type = FieldType.Keyword)
    private String bookNo;

    @Field(analyzer = "ik_max_word", type = FieldType.Text)
    private String bookName;
    @Field(analyzer = "ik_max_word", type = FieldType.Text)
    private String subTitle;
    @Field(analyzer = "ik_max_word", type = FieldType.Text)
    private String keywords;

    private Integer categoryId;
    @Field(type = FieldType.Keyword)
    private String categoryName;

    private BigDecimal price;
    private Integer stock;

    private Integer status;
    @Field(type = FieldType.Date, format = DateFormat.basic_date_time)
    private Date publishDate;
}
